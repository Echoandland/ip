# AI-Assisted Development

This document describes how AI tools were used in the development of this project.

## Tools Used

- **Cursor IDE** with AI assistance (Claude/GPT models)

## How AI Helped

1. **Week 6 Tutorial - Debugging Setup**
   - Created `.vscode/launch.json` for Java debugging configuration
   - Added `main` method to `Main.java` to resolve "Main method not found" when running directly from the editor

2. **Week 6 iP Tasks**
   - Implemented `bye` command handling in GUI: added `Platform.exit()` when user types "bye"
   - Added `isExitCommand()` method to `ChatBot` to support graceful app closure
   - Created this `AI.md` file and updated User Guide

3. **Code Structure**
   - The project follows the Command pattern and OOP design from the iP template; AI assisted with incremental features and debugging configurations.
