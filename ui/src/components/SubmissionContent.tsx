"use client";

import { useState } from "react";
import { Button } from "./ui/button";
import { formatDistanceToNow } from "date-fns";
import SyntaxHighlighter from "react-syntax-highlighter";
import { vs2015 } from "react-syntax-highlighter/dist/esm/styles/hljs";
import { capitalize, cn } from "@/lib/utils";
import Icons from "@/lib/icons";

export default function SubmissionContent({ submission }: { submission: any }) {
  const [show, setShow] = useState(false);

  return (
    <div
      key={submission.id}
      className="flex flex-col gap-2 border border-neutral-200 p-4 rounded-lg"
    >
      <p
        className={cn("font-semibold", {
          "text-green-600": submission.run.verdict === "Accepted",
          "text-red-600": submission.run.verdict !== "Accepted",
        })}
      >
        {submission.run.verdict || "Unavailable"}
      </p>
      <p className="flex items-center gap-1 justify-start">
        <Icons.lang className="h-4 w-4" />
        {capitalize(submission.run.lang)}
      </p>
      <p className="flex items-center gap-1 justify-start">
        <Icons.clock className="h-4 w-4" />
        {formatDistanceToNow(new Date(submission.createdAt))} ago
      </p>
      <Button
        className="flex items-center justify-center gap-1"
        variant="outline"
        onClick={() => setShow((show) => !show)}
      >
        <Icons.code className="h-4 w-4" />
        {show ? "Hide" : "Show"} Code
      </Button>
      {show && (
        <div className="max-h-[500px] overflow-auto rounded-lg">
          <SyntaxHighlighter language={submission.run.lang} style={vs2015}>
            {submission.run.code}
          </SyntaxHighlighter>
        </div>
      )}
    </div>
  );
}
