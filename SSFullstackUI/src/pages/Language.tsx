import { useEffect, useRef, useState } from "react";
import api from "../api/client";

function Language() {
  const nameRef = useRef<HTMLInputElement | null>(null);
  type LanguageItem = { id: number; name: string };
  const [items, setItems] = useState<LanguageItem[]>([]);
  const [editingId, setEditingId] = useState<number | null>(null);

  const load = async () => {
    try {
      const res = await api.get(`/languages`, { params: { page: 0, size: 100 } });
      setItems(res?.data?.content ?? []);
    } catch {
      // silent
    }
  };

  useEffect(() => { load(); }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const name = nameRef.current?.value?.trim();
    if (!name) return;
    try {
      if (editingId == null) {
        await api.post(`/languages`, { name });
      } else {
        await api.put(`/languages/${editingId}`, { name });
      }
      if (nameRef.current) nameRef.current.value = "";
      setEditingId(null);
      load();
    } catch {
      // keep UI unchanged
    }
  };

  const handleEdit = (it: LanguageItem) => {
    if (nameRef.current) nameRef.current.value = it.name;
    setEditingId(it.id);
  };

  const handleDelete = async (id: number) => {
    try { await api.delete(`/languages/${id}`); load(); } catch { /* silent */ }
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

      <div className="row mb-3">
        <table className="table table-bordered table-striped align-middle">
          <thead>
            <tr>
              <th>Id</th>
              <th>Name</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {items.map(it => (
              <tr key={it.id}>
                <td>{it.id}</td>
                <td>{it.name}</td>
                <td className="d-flex gap-2">
                  <button type="button" className="btn btn-primary btn-sm" onClick={() => handleEdit(it)}>Edit</button>
                  <button type="button" className="btn btn-danger btn-sm" onClick={() => handleDelete(it.id)}>Delete</button>
                </td>
              </tr>
            ))}
            {items.length === 0 && (
              <tr><td colSpan={3}>No data</td></tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default Language