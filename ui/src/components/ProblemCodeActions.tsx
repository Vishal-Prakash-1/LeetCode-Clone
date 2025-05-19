"use client";

import { useState } from "react";
import useTestSubmissionMutation from "@/hooks/useTestSubmissionMutation";
import useSubmissionMutation from "@/hooks/useSubmissionMutation";
import { Button } from "./ui/button";
import Icons from "@/lib/icons";
import RunContent from "./RunContent";
import { cn } from "@/lib/utils";

export default function ProblemCodeActions({
  problem,
  lang,
  code,
}: {
  problem: any;
  lang: string;
  code?: string;
}) {
  const [show, setShow] = useState(false);
  const [rid, setRid] = useState<string | undefined>();

  const { mutateAsync: mutateTestSubmission, isPending: isRunning } =
    useTestSubmissionMutation(problem.id, lang, code);
  const { mutateAsync: mutateSubmission, isPending: isSubmitting } =
    useSubmissionMutation(problem.id, lang, code);

  async function runHandler() {
    const testSubmission = await mutateTestSubmission();
    setRid(testSubmission.rid);
    setShow(true);
  }

  async function submitHandler() {
    const submission = await mutateSubmission();
    setRid(submission.run.id);
    setShow(true);
  }

  return (
    <div
      className={cn(
        "flex flex-col gap-4 overflow-auto border border-neutral-200 rounded-lg p-2",
        show && "h-[500px]"
      )}
    >
      <div className="flex justify-between">
        <div className="flex gap-2">
          <Button
            className="inline-flex gap-2 items-center justify-between"
            onClick={runHandler}
            disabled={isRunning}
          >
            {isRunning && <Icons.loader className="h-5 w-5 animate-spin" />}
            Run
          </Button>
          <Button
            className="inline-flex gap-2 items-center justify-between"
            onClick={submitHandler}
            disabled={isSubmitting}
          >
            {isSubmitting && <Icons.loader className="h-4 w-4 animate-spin" />}
            Submit
          </Button>
        </div>
        <Button variant="outline" onClick={() => setShow((show) => !show)}>
          {show ? <Icons.chevronDown /> : <Icons.chevronUp />}
        </Button>
      </div>
      <div className={cn(show ? "visible" : "hidden")}>
        <hr className="mb-4" />
        <RunContent rid={rid} />
      </div>
    </div>
  );
}
