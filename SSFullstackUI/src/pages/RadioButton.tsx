import { useEffect, useRef, useState } from 'react';
import api from '../api/client';

type Student = {
  id?: number;
  name: string;
  email: string;
  mobile: string;
  country: string;
  state: string;
  district: string;
  gender: string;
};

function RadioButton() {
  const nameRef = useRef<HTMLInputElement | null>(null);
  const emailRef = useRef<HTMLInputElement | null>(null);
  const mobileRef = useRef<HTMLInputElement | null>(null);
  const countryRef = useRef<HTMLInputElement | null>(null);
  const stateRef = useRef<HTMLInputElement | null>(null);
  const districtRef = useRef<HTMLInputElement | null>(null);
  const [gender, setGender] = useState<string>('Male');
  const [rows, setRows] = useState<Student[]>([]);
  const [editingId, setEditingId] = useState<number | null>(null);

  const load = async () => {
    try { const res = await api.get('/students'); setRows(res?.data ?? []); } catch { /* silent */ }
  };
  useEffect(() => { load(); }, []);

  const onSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const payload = {
      name: nameRef.current?.value?.trim() || '',
      email: emailRef.current?.value?.trim() || '',
      mobile: mobileRef.current?.value?.trim() || '',
      country: countryRef.current?.value?.trim() || '',
      state: stateRef.current?.value?.trim() || '',
      district: districtRef.current?.value?.trim() || '',
      gender,
    };
    try {
      if (editingId == null) {
        await api.post('/students', payload);
      } else {
        await api.put(`/students/${editingId}`, payload);
      }
      if (nameRef.current) nameRef.current.value = '';
      if (emailRef.current) emailRef.current.value = '';
      if (mobileRef.current) mobileRef.current.value = '';
      if (countryRef.current) countryRef.current.value = '';
      if (stateRef.current) stateRef.current.value = '';
      if (districtRef.current) districtRef.current.value = '';
      setGender('Male');
      setEditingId(null);
      load();
    } catch { /* silent */ }
  };

  const onEdit = (r: Student) => {
    if (nameRef.current) nameRef.current.value = r.name || '';
    if (emailRef.current) emailRef.current.value = r.email || '';
    if (mobileRef.current) mobileRef.current.value = r.mobile || '';
    if (countryRef.current) countryRef.current.value = r.country || '';
    if (stateRef.current) stateRef.current.value = r.state || '';
    if (districtRef.current) districtRef.current.value = r.district || '';
    setGender(r.gender || 'Male');
    setEditingId(r.id ?? null);
  };

  const onDelete = async (id?: number) => {
    if (!id) return;
    try { await api.delete(`/students/${id}`); load(); } catch { /* silent */ }
  };

  return (
    <div className="container">
      <h2>Manage Student</h2>
      <form onSubmit={onSubmit}>
        <div className="row mb-3">
          <div className="col">
            <input ref={nameRef} type="text" placeholder="Enter Name" className="form-control" />
          </div>
          <div className="col">
            <input ref={emailRef} type="text" placeholder="Enter Email" className="form-control" />
          </div>
          <div className="col">
            <input ref={mobileRef} type="text" placeholder="Enter Mobile" className="form-control" />
          </div>
        </div>
        <div className="row mb-3">
          <div className="col">
            <input ref={countryRef} type="text" placeholder="Enter Country" className="form-control" />
          </div>
          <div className="col">
            <input ref={stateRef} type="text" placeholder="Enter State" className="form-control" />
          </div>
          <div className="col">
            <input ref={districtRef} type="text" placeholder="Enter District" className="form-control" />
          </div>
        </div>
        <div className="row mb-3 text-center">
          <div className="col">
            Gender
            <label className="ms-2"><input type="radio" name="gender" value="Male" checked={gender==='Male'} onChange={() => setGender('Male')} /> Male</label>
            <label className="ms-2"><input type="radio" name="gender" value="Female" checked={gender==='Female'} onChange={() => setGender('Female')} /> Female</label>
            <label className="ms-2"><input type="radio" name="gender" value="Other" checked={gender==='Other'} onChange={() => setGender('Other')} /> Other</label>
          </div>
          <div className="col">
            <button className="btn btn-primary">Add Student</button>
          </div>
          <div className="col"></div>          
        </div>
      </form>

      <div className="row">
        <div className="col">
          <table className="table table-bordered table-striped align-middle">
            <thead>
              <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Email</th>
                <th>Mobile</th>
                <th>Country</th>
                <th>State</th>
                <th>District</th>
                <th>Gender</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {rows.map((r: Student) => (
                <tr key={r.id}>
                  <td>{r.id}</td>
                  <td>{r.name}</td>
                  <td>{r.email}</td>
                  <td>{r.mobile}</td>
                  <td>{r.country}</td>
                  <td>{r.state}</td>
                  <td>{r.district}</td>
                  <td>{r.gender}</td>
                  <td className="d-flex gap-2">
                    <button type="button" className="btn btn-primary btn-sm" onClick={() => onEdit(r)}>Edit</button>
                    <button type="button" className="btn btn-danger btn-sm" onClick={() => onDelete(r.id)}>Delete</button>
                  </td>
                </tr>
              ))}
              {rows.length === 0 && (
                <tr><td colSpan={9}>No data</td></tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

export default RadioButton;