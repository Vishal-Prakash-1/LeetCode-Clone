"use client";

import { useEffect, useState } from "react";
import Editor from "./Editor";
import LanguageSelector from "./LanguageSelector";
import ProblemCodeActions from "./ProblemCodeActions";
import CONSTANTS from "@/lib/constants";
import { Button } from "./ui/button";
import Icons from "@/lib/icons";
import Link from "next/link";

export default function ProblemCodeSection({ problem }: { problem: any }) {
  const [lang, setLang] = useState(CONSTANTS.DEFAULT_LANG);
  const [code, setCode] = useState<string | undefined>();

  useEffect(() => {
    setCode(problem.templateCodeList.find((tc: any) => tc.lang === lang).code);
  }, [lang, problem.templateCodeList]);

  return (
    <div className="flex flex-col gap-2 h-full">
      <div className="flex justify-between">
        <LanguageSelector value={lang} onChange={setLang} />
        <Button
          variant="outline"
          className="inline-flex gap-2 justify-center items-center"
          asChild
        >
          <Link href="/">
            <Icons.undo className="h-4 w-4" />
            Problems
          </Link>
        </Button>
      </div>
      <div className="flex-1 overflow-auto">
        <Editor language={lang} value={code} onChange={setCode} />
      </div>
      <ProblemCodeActions problem={problem} lang={lang} code={code} />
    </div>
  );
}
