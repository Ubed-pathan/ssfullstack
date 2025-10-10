import { useEffect, useRef, useState } from "react";
import api from "../api/client";

function ImageUpload() {
  const nameRef = useRef<HTMLInputElement | null>(null);
  const emailRef = useRef<HTMLInputElement | null>(null);
  const mobileRef = useRef<HTMLInputElement | null>(null);
  const fileRef = useRef<HTMLInputElement | null>(null);
  const [busy, setBusy] = useState(false);
  const [editingId, setEditingId] = useState<number | null>(null);

  type CustomerItem = { id: number; name: string; email: string; mobile: string; imageUrl: string };
  const [rows, setRows] = useState<CustomerItem[]>([]);

  const load = async () => {
    try {
      const res = await api.get(`/customers`);
      if (Array.isArray(res?.data)) {
        const sorted = [...res.data].sort((a, b) => (a?.id ?? 0) - (b?.id ?? 0));
        setRows(sorted);
      } else {
        setRows([]);
      }
    } catch {
      // silent
    }
  };

  useEffect(() => { load(); }, []);

  const onSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
  const fd = new FormData();
  const files = fileRef.current?.files;
  const hasFile = !!files && files.length > 0;
    if (!hasFile && editingId == null) return; // creating requires file
  if (hasFile && files) fd.append("file", files[0]);
    try {
      setBusy(true);
      // Create customer with image upload in one request
      fd.append("name", nameRef.current?.value?.trim() || "");
      fd.append("email", emailRef.current?.value?.trim() || "");
      fd.append("mobile", mobileRef.current?.value?.trim() || "");
      if (editingId == null) {
        await api.post(`/customers`, fd);
      } else {
        await api.put(`/customers/${editingId}`, fd);
      }
      // clear form & reload table
      if (nameRef.current) nameRef.current.value = "";
      if (emailRef.current) emailRef.current.value = "";
      if (mobileRef.current) mobileRef.current.value = "";
      if (fileRef.current) fileRef.current.value = "";
      setEditingId(null);
      await load();
    } finally {
      setBusy(false);
    }
  };

  const handleDelete = async (id: number) => {
    try { await api.delete(`/customers/${id}`); load(); } catch { /* silent */ }
  };

  const handleEdit = (r: CustomerItem) => {
    if (nameRef.current) nameRef.current.value = r.name || "";
    if (emailRef.current) emailRef.current.value = r.email || "";
    if (mobileRef.current) mobileRef.current.value = r.mobile || "";
    if (fileRef.current) fileRef.current.value = ""; // clear; new file optional
    setEditingId(r.id);
  };

  return (
    <div className="container">
      <h2>Manage Customer</h2>
      <form onSubmit={onSubmit} encType="multipart/form-data">
        <div className="row mb-3">
          <div className="col">
            <input ref={nameRef} type="text" placeholder="Enter Name" className="form-control" />
          </div>
          <div className="col">
            <input ref={emailRef} type="text" placeholder="Enter Email" className="form-control" />
          </div>
        </div>
        <div className="row mb-3">
          <div className="col">
            <input ref={mobileRef} type="text" placeholder="Enter Mobile" className="form-control" />
          </div>
          <div className="col">
            <input ref={fileRef} type="file" className="form-control" accept="image/*" />
          </div>
        </div>
        <div className="row mb-3 text-center">
          <div className="col">
            <button className="btn btn-primary" disabled={busy}>{busy ? (editingId==null?"Uploading...":"Saving...") : (editingId==null?"Add Customer":"Save Changes")}</button>
            {editingId != null && (
              <button type="button" className="btn btn-secondary ms-2" onClick={() => {
                if (nameRef.current) nameRef.current.value = "";
                if (emailRef.current) emailRef.current.value = "";
                if (mobileRef.current) mobileRef.current.value = "";
                if (fileRef.current) fileRef.current.value = "";
                setEditingId(null);
              }}>Cancel Edit</button>
            )}
          </div>
        </div>
      </form>

      {/* Uploaded list (session only) */}
      <div className="row mt-3">
        <div className="col">
          <table className="table table-bordered table-striped align-middle">
            <thead>
              <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Email</th>
                <th>Mobile</th>
                <th>Image</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {rows.map(r => (
                <tr key={r.id}>
                  <td>{r.id}</td>
                  <td>{r.name}</td>
                  <td>{r.email}</td>
                  <td>{r.mobile}</td>
                  <td>
                    <img src={r.imageUrl} alt={r.name || 'uploaded'} style={{ width: 60, height: 40, objectFit: 'cover' }} />
                  </td>
                  <td className="d-flex gap-2">
                    <button type="button" className="btn btn-primary btn-sm" onClick={() => handleEdit(r)}>Edit</button>
                    <button type="button" className="btn btn-danger btn-sm" onClick={() => handleDelete(r.id)}>Delete</button>
                  </td>
                </tr>
              ))}
              {rows.length === 0 && (
                <tr>
                  <td colSpan={6}>No data</td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
      {/* Removed inline uploaded image preview as requested */}
    </div>
  )
}

export default ImageUpload
