export async function getRun(rid: string) {
  const res = await fetch(`http://localhost:8080/run/${rid}`, {
    cache: "no-store",
  });
  if (!res.ok) {
    throw new Error("Failed to fetch run");
  }

  const run = await res.json();
  return run;
}
