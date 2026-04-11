#!/usr/bin/env python3
import sys

file_path = "src/core_game_entities/bosses/BossEntities.java"

with open(file_path, "r") as f:
    lines = f.readlines()

# Track brace stack to find mismatch
stack = []
for i, line in enumerate(lines, 1):
    for j, char in enumerate(line):
        if char == '{':
            stack.append(('open', i, j, line.strip()))
        elif char == '}':
            if stack:
                stack.pop()
            else:
                print(f"EXTRA CLOSING BRACE at Line {i}, Column {j}")
                print(f"  Line content: {line.strip()}")
                sys.exit(1)

opens = sum(1 for c in ''.join(lines) if c == '{')
closes = sum(1 for c in ''.join(lines) if c == '}')
print(f"Total opening braces: {opens}")
print(f"Total closing braces: {closes}")
print(f"Unmatched opening braces: {len(stack)}")

if stack:
    print("\nUnmatched opening braces:")
    for op_type, line, col, content in stack[-5:]:
        print(f"  Line {line}: {content}")

if opens == closes:
    print("\nBraces are balanced!")
else:
    print(f"\nBrace mismatch: {opens - closes} extra opening braces")
