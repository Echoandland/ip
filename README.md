# ChatBot

ChatBot is a desktop task management application with a chat-style interface. It helps users manage todos, deadlines, events, and do-within tasks.

## Running the App

Build the runnable JAR with:

```bash
./gradlew clean shadowJar
```

Run the application with:

```bash
java -jar build/libs/ChatBot.jar
```

## Documentation

- User Guide: [GitHub Pages site](https://echoandland.github.io/ip/)

## Development Notes

- The JavaFX GUI entry point is `Main`
- The text-based entry point is `chatbot.ChatBot`
- Saved data is stored in `data/ChatBot.txt`
