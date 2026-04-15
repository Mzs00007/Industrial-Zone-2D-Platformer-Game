# _unused — Archived / Dead Code

These files were moved here during codebase cleanup (April 2026).
They are **not compiled** and have no effect on the game.
They are kept for reference only.

## What is here and why

### `animation/` (353 files)
Auto-generated reference/documentation files from the asset pack survey.
Only **`HorizontalSpritesheetLoader.java`** from this package is actually imported
by `Game.java` — it remains in `src/animation/`. Everything else here was
never referenced anywhere in the active codebase.

### `tests/` (2 files)
Old standalone test harness files that were part of earlier development
iterations. The game does not depend on them.

### `important/` (17 files)
Archived design-reference files kept from an earlier restructuring phase.
None are imported by `Game.java` or any active source file.

### `old_numbering/` — `12_Tests/`, `4_Entities/`
Leftover folders from a previous numbered reorganisation attempt.
Content superseded by the proper package structure in `src/`.

---

> To restore any file: move it back to its proper package folder under `src/`
> and re-compile with `RUN_GAME.bat`.
