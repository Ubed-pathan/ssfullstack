import { useCallback, useEffect, useRef, useState } from "react";
import api from "../api/client";

function District() {
  type DistrictItem = { id: number; name: string; state: string };
  type DistrictRow = DistrictItem & { country?: string };
  const countryRef = useRef<HTMLInputElement | null>(null);
  const stateRef = useRef<HTMLInputElement | null>(null);
  const districtRef = useRef<HTMLInputElement | null>(null);
  const [items, setItems] = useState<DistrictRow[]>([]);
  const [editingId, setEditingId] = useState<number | null>(null);

  const load = useCallback(async () => {
    try {
      const res = await api.get(`/districts`, { params: { page: 0, size: 100 } });
      const rawItems: DistrictItem[] = res?.data?.content ?? [];
      const uniqueStates = Array.from(new Set(rawItems.map(i => i.state).filter(Boolean)));
      const lookups = await Promise.all(uniqueStates.map(async (st) => {
        try {
          const r = await api.get(`/states`, { params: { q: st, size: 1 } });
          const country = r?.data?.content?.[0]?.country as string | undefined;
          return [st, country] as const;
        } catch { return [st, undefined] as const; }
      }));
      const map = new Map<string, string | undefined>(lookups);
      const rows: DistrictRow[] = rawItems.map(it => ({ ...it, country: map.get(it.state) }));
      setItems(rows);
    } catch { /* silent */ }
  }, []);

  useEffect(() => { load(); }, [load]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const name = districtRef.current?.value?.trim();
    const stateVal = stateRef.current?.value?.trim();
    if (!name || !stateVal) return;
    try {
      if (editingId == null) {
        await api.post(`/districts`, { name, state: stateVal });
      } else {
        await api.put(`/districts/${editingId}`, { name, state: stateVal });
      }
      if (districtRef.current) districtRef.current.value = "";
      if (stateRef.current) stateRef.current.value = "";
      if (countryRef.current) countryRef.current.value = "";
      setEditingId(null);
      load();
    } catch {
      // keep UI unchanged; no visible error handling added
    }
  };

  const handleEdit = (it: DistrictRow) => {
    if (countryRef.current) countryRef.current.value = it.country ?? "";
    if (stateRef.current) stateRef.current.value = it.state;
    if (districtRef.current) districtRef.current.value = it.name;
    setEditingId(it.id);
  };

  const handleDelete = async (id: number) => {
    try { await api.delete(`/districts/${id}`); load(); } catch { /* silent */ }
  };

  return (
    <div className="container">
      <h2>Manage District</h2>
      <form onSubmit={handleSubmit}>
        <div className="row mb-3">
          <div className="col">
            <input type="text" placeholder="Enter Country" className="form-control" ref={countryRef} />
          </div>
          <div className="col">
            <input type="text" placeholder="Enter State" className="form-control" ref={stateRef} />
          </div>
          <div className="col">
            <input type="text" placeholder="Enter District" className="form-control" ref={districtRef} />
          </div>
        </div>
        <div className="row mb-3 text-center">
          <div className="col">
            <button className="btn btn-primary">Add District</button>
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
              <th>District</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {items.map(it => (
              <tr key={it.id}>
                <td>{it.id}</td>
                <td>{it.country ?? '-'}</td>
                <td>{it.state}</td>
                <td>{it.name}</td>
                <td className="d-flex gap-2">
                  <button type="button" className="btn btn-primary btn-sm" onClick={() => handleEdit(it)}>Edit</button>
                  <button type="button" className="btn btn-danger btn-sm" onClick={() => handleDelete(it.id)}>Delete</button>
                </td>
              </tr>
            ))}
            {items.length === 0 && (
              <tr><td colSpan={5}>No data</td></tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  )
}

export default District
