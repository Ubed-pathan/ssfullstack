import { useEffect, useRef, useState } from "react";
import api from "../api/client";

function State() {
  
  const countryRef = useRef<HTMLInputElement | null>(null);
  const stateRef = useRef<HTMLInputElement | null>(null);
  type StateItem = { id: number; name: string; country: string };
  const [items, setItems] = useState<StateItem[]>([]);
  const [editingId, setEditingId] = useState<number | null>(null);

  const load = async () => {
    try {
      const res = await api.get(`/states`, { params: { page: 0, size: 100 } });
      setItems(res?.data?.content ?? []);
    } catch { /* silent */ }
  };

  useEffect(() => { load(); }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const name = stateRef.current?.value?.trim();
    const countryVal = countryRef.current?.value?.trim();
    if (!name || !countryVal) return;
    try {
      if (editingId == null) {
        await api.post(`/states`, { name, country: countryVal });
      } else {
        await api.put(`/states/${editingId}`, { name, country: countryVal });
      }
      if (stateRef.current) stateRef.current.value = "";
      if (countryRef.current) countryRef.current.value = "";
      setEditingId(null);
      load();
    } catch {
      // keep UI unchanged; no visible error handling added
    }
  };

  const handleEdit = (it: StateItem) => {
    if (countryRef.current) countryRef.current.value = it.country;
    if (stateRef.current) stateRef.current.value = it.name;
    setEditingId(it.id);
  };

  const handleDelete = async (id: number) => {
    try { await api.delete(`/states/${id}`); load(); } catch { /* silent */ }
  };

  return (
    <div className="container">
      <h2>Manage State</h2>
      <form onSubmit={handleSubmit}>
        <div className="row mb-3">
            <div className="col">
                <input type="text" placeholder="Enter Country" className="form-control" ref={countryRef} />
            </div>
            <div className="col">
                <input type="text" placeholder="Enter State" className="form-control" ref={stateRef} />
            </div>
        </div>
        <div className="row mb-3 text-center">
          <div className="col">
            <button className="btn btn-primary">Add State</button>
          </div>
        </div>
      </form>
      <div className="row mb-3">
        <table className="table table-bordered table-striped align-middle">
          <thead>
            <tr>
              <th>Id</th>
              <th>Country</th>
              <th>State</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {items.map(it => (
              <tr key={it.id}>
                <td>{it.id}</td>
                <td>{it.country}</td>
                <td>{it.name}</td>
                <td className="d-flex gap-2">
                  <button type="button" className="btn btn-primary btn-sm" onClick={() => handleEdit(it)}>Edit</button>
                  <button type="button" className="btn btn-danger btn-sm" onClick={() => handleDelete(it.id)}>Delete</button>
                </td>
              </tr>
            ))}
            {items.length === 0 && (
              <tr><td colSpan={4}>No data</td></tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  )
}

export default State
