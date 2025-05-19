import { postSubmission } from "@/lib/api/submission";
import { useMutation } from "@tanstack/react-query";

export default function useSubmissionMutation(
  pid: string,
  lang: string,
  code?: string
) {
  return useMutation({
    mutationFn: async () => {
      const run = await postSubmission(pid, lang, code);
      return run;
    },
  });
}
