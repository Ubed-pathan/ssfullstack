import { useRef } from "react";
import axios from "axios";

function Language() {
  const API_BASE = import.meta.env.VITE_API_BASE || "http://localhost:8080/api";
  const nameRef = useRef<HTMLInputElement | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const name = nameRef.current?.value?.trim();
    if (!name) return;
    try {
      await axios.post(`${API_BASE}/languages`, { name });
      if (nameRef.current) nameRef.current.value = "";
    } catch {
      // no UI change; keep silent to preserve layout/UX
    }
  };

  return (
    <div className="container">
      <h2>Manage Language</h2>
      <form onSubmit={handleSubmit}>
        <div className="row mb-3">
          <div className="col">
            <input type="text" placeholder="Enter Language Name" className="form-control" ref={nameRef} />
          </div>
        </div>
        <div className="row mb-3 text-center">
          <div className="col">
            <button type="submit" className="btn btn-primary">Add Language</button>
          </div>
        </div>
      </form>
    </div>
  );
}

export default Language