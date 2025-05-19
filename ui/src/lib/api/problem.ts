export async function getProblems() {
  const resp = await fetch("http://localhost:8080/problem", {
    cache: "force-cache",
    next: {
      tags: ["problems"],
    },
  });
  if (!resp.ok) {
    throw new Error("Failed to fetch problems");
  }

  const problems = await resp.json();
  return problems;
}

export async function getProblem(pid: string) {
  const res = await fetch(`http://localhost:8080/problem/${pid}`, {
    cache: "force-cache",
    next: {
      tags: [`problem-${pid}`],
    },
  });
  if (!res.ok) {
    throw new Error("Failed to fetch problem");
  }

  const problem = await res.json();
  return problem;
}

export async function getProblemSubmissions(pid: string) {
  const res = await fetch(`http://localhost:8080/problem/${pid}/submission`, {
    cache: "no-store",
  });
  if (!res.ok) {
    throw new Error("Failed to fetch problem submissions");
  }

  const data = await res.json();
  return data;
}

export async function postProblem(problemBody: any) {
  const res = await fetch("http://localhost:8080/problem", {
    headers: {
      "Content-Type": "application/json",
    },
    method: "POST",
    body: JSON.stringify(problemBody),
  });
  if (!res.ok) {
    throw new Error("Failed to post problem");
  }

  const data = await res.json();
  return data;
}

export async function putProblem(pid: string, problemBody: any) {
  const res = await fetch(`http://localhost:8080/problem/${pid}`, {
    headers: {
      "Content-Type": "application/json",
    },
    method: "PUT",
    body: JSON.stringify(problemBody),
  });
  if (!res.ok) {
    throw new Error("Failed to put problem");
  }

  const data = await res.json();
  return data;
}

export async function deleteProblem(pid: any) {
  const res = await fetch(`http://localhost:8080/problem/${pid}`, {
    method: "DELETE",
  });
  if (!res.ok) {
    throw new Error("Failed to delete problem");
  }
}
