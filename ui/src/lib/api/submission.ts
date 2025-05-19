export async function postTestSubmission(
  pid: string,
  lang: string,
  code?: string
) {
  const res = await fetch("http://localhost:8080/submission/test", {
    headers: {
      "Content-Type": "application/json",
    },
    method: "POST",
    body: JSON.stringify({
      pid,
      lang,
      code,
    }),
  });

  if (!res.ok) {
    throw new Error("Failed to post test submission");
  }

  const data = await res.json();
  return data;
}

export async function postSubmission(pid: string, lang: string, code?: string) {
  const res = await fetch("http://localhost:8080/submission", {
    headers: {
      "Content-Type": "application/json",
    },
    method: "POST",
    body: JSON.stringify({
      pid,
      lang,
      code,
    }),
  });

  if (!res.ok) {
    throw new Error("Failed to post submission");
  }

  const data = await res.json();
  return data;
}
