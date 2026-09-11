# ZAKA AI Architecture

How the tutoring intelligence in ZAKA Hack Academy is put together, and — just as importantly —
what it does *not* do.

## Pipeline

```
User query
  ↓ QueryRouter        detect language (English / Roman Urdu), intent, target chapter/topic
  ↓ KnowledgeRetriever retrieve only the relevant course notes (never the whole database)
  ↓ Provider chain     first configured provider that answers wins
  ↓ ChatMessage        answer + the provider that actually produced it
```

`ZakaAiEngine.processQuery(rawQuery, history)` runs that pipeline for chat.
`ZakaAiEngine.evaluateWrittenAnswer(question, studentAnswer)` runs a second, narrower pipeline used
by the Question Tester and the exam engine.

## Providers

| Provider | When it is used | Requires |
| --- | --- | --- |
| `GeminiProvider` | Selected in Settings and an API key is present | `GEMINI_API_KEY` (never committed) |
| `OllamaLanProvider` | Selected in Settings, LAN host reachable | A local Ollama server |
| `OfflineExpertProvider` | Default, and the fallback whenever a cloud/LAN call fails | Nothing — ships with the app |

Provider selection never silently degrades quality without saying so: the message and the evaluation
card both carry the name of the component that actually answered
(for example `ZAKA Offline Rubric` versus `Gemini`).

## Retrieval, not stuffing

`KnowledgeRetriever` scores topics against the query and passes only the matching notes into the
prompt. This keeps prompts small and, more importantly, keeps answers anchored to the eight
chapters of the actual course rather than to the model's general memory.

## Answer evaluation

Written answers are graded by `AnswerEvaluator`, a deterministic offline rubric:

1. Extract significant terms from the model answer and the student answer (stop words removed,
   prefix-based fuzzy matching so `encrypt` / `encryption` count as the same idea).
2. Measure coverage of model-answer terms and of the question's listed key points.
3. Weight by completeness (answer length relative to the expected answer).
4. Map the score: `>= 75` → ✅ Correct, `>= 40` → 🟡 Partially Correct, otherwise ❌ Incorrect.

There is no exact-text comparison anywhere in the path — a correct answer written in the student's
own words scores as correct (covered by unit tests).

If a cloud/LAN provider is configured, `evaluateWrittenAnswer` asks it for a structured verdict
(`SCORE / VERDICT / RIGHT / MISSING / FEEDBACK / IMPROVED`) and parses it. If the provider is absent,
errors, times out or returns something unparseable, the offline rubric result is used instead and
the card says so. Feedback is always labelled:

> AI Practice Evaluation — study feedback only, not an official board examiner score.

## Error Solver

`ErrorSolver.analyze` is a static analyser. It separates:

- **Observed facts** — things literally present in the pasted text (error type, line number, file
  name, unbalanced brackets).
- **Likely causes** — pattern-matched explanations for known error signatures.
- **Suggested fixes** — concrete next steps.
- **Needs execution to confirm** — everything that cannot be known without running the code.

The app does not execute user code on the device, and every analysis states that explicitly rather
than implying a program was run.

## Honesty rules encoded in the code

- Unknown error text produces "No known error signature was recognised", not an invented cause.
- Chapters with no recorded attempts show "No attempts recorded yet", not a placeholder score.
- The evaluation card names the evaluator, so offline grading is never presented as cloud AI.
