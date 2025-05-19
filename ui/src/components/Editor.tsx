"use client";

import { Editor as MonacoEditor, EditorProps } from "@monaco-editor/react";

export default function Editor(props: EditorProps) {
  return (
    <MonacoEditor
      options={{
        padding: {
          top: 8,
          bottom: 8,
        },
        bracketPairColorization: {
          enabled: true,
        },
        fontSize: 15,
      }}
      theme="vs-dark"
      {...props}
    />
  );
}
