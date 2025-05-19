"use server";

import { revalidateTag } from "next/cache";
import { deleteProblem, postProblem, putProblem } from "../api/problem";

export async function postProblemAction(problemBody: any) {
  const data = await postProblem(problemBody);
  revalidateTag("problems");
  return data;
}

export async function putProblemAction(pid: string, problemBody: any) {
  const data = await putProblem(pid, problemBody);
  revalidateTag(`problem-${pid}`);
  revalidateTag("problems");
  return data;
}

export async function deleteProblemAction(pid: string) {
  await deleteProblem(pid);
  revalidateTag("problems");
}
