import { useEffect, useState } from "react";
import Swal from "sweetalert2";
import api from "../api/client";

function Country() {
  type CountryItem = { id: number; name: string; imageUrl?: string };
  const [countries, setCountries] = useState<CountryItem[]>([]);
  const [id, setId] = useState(0);
  const [name, setName] = useState("");
  const [image, setImage] = useState<File | null>(null);
  

  useEffect(() => {
    loadCountries();
  }, []);

  // Load countries
  const loadCountries = async () => {
    try {
      const res = await api.get(`/countries`, { params: { page: 0, size: 200 } });
      setCountries(res?.data?.content ?? []);
    } catch {
      // silent
    }
  };

  // Toast helper
  const toast = (icon: "success" | "error" | "warning", title: string) => {
    Swal.fire({
      toast: true,
      position: "top-end",
      icon,
      title,
      showConfirmButton: false,
      timer: 2000,
      timerProgressBar: true,
    });
  };

  // Save (Add/Update)
  const handleSave = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      if (id === 0) {
        // Create country first
        const createRes = await api.post(`/countries`, { name });
        const created: CountryItem = createRes.data;
        // If image selected, upload it
        if (image) {
          const fd = new FormData();
          fd.append("file", image);
          await api.post(`/countries/${created.id}/image`, fd, { headers: { "Content-Type": "multipart/form-data" } });
        }
        toast("success", "Country added");
      } else {
        // Update country name
        await api.put(`/countries/${id}`, { name });
        // Optionally replace image
        if (image) {
          const fd = new FormData();
          fd.append("file", image);
          await api.post(`/countries/${id}/image`, fd, { headers: { "Content-Type": "multipart/form-data" } });
        }
        toast("success", "Country updated");
      }
      resetForm();
      loadCountries();
    } catch {
      toast("error", "Operation failed");
    }
  };

  // Edit
  const handleEdit = (country: CountryItem) => {
    setId(country.id);
    setName(country.name);
  };

  // Delete
  const handleDelete = (countryId: number) => {
    Swal.fire({
      title: "Delete country?",
      text: "This cannot be undone!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#d33",
      cancelButtonColor: "#3085d6",
      confirmButtonText: "Yes",
    }).then((result) => {
      if (result.isConfirmed) {
        api.delete(`/countries/${countryId}`).then(() => {
          toast("success", "Country deleted");
          loadCountries();
        });
      }
    });
  };

  // Reset form
  const resetForm = () => {
    setId(0);
    setName("");
    setImage(null);
  };

  return (
    <div className="container">
      <h2>Manage Country</h2>
      <form onSubmit={handleSave} encType="multipart/form-data">
        <div className="row mb-3">
          <div className="col">
            <input
              type="text"
              className="form-control"
              value={name}
              placeholder="Enter country name"
              onChange={(e) => setName(e.target.value)}
              required
            />
          </div>
        </div>

        <div className="col mb-3">
          <label>Upload Image</label>
          <input
            type="file"
            accept="image/*"
            className="form-control"
            onChange={(e) => {
              if (e.target.files && e.target.files.length > 0) {
                setImage(e.target.files[0]);
              }
            }}
            required={id === 0}
          />
        </div>

        <div className="row mb-3 text-center">
          <div className="col">
            <button type="submit" className="btn btn-primary">
              {id === 0 ? "Add Country" : "Update Country"}
            </button>
          </div>
        </div>
      </form>

      <table className="table table-bordered table-striped">
        <thead>
          <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Image</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {countries.map((c: CountryItem) => (
            <tr key={c.id}>
              <td>{c.id}</td>
              <td>{c.name}</td>
              <td>
                {c.imageUrl ? (
                  <img
                    src={c.imageUrl}
                    alt={c.name}
                    style={{ width: "60px", height: "40px", objectFit: "cover" }}
                  />
                ) : (
                  <span>No image</span>
                )}
              </td>
              <td>
                <button
                  className="btn btn-sm btn-warning me-2"
                  onClick={() => handleEdit(c)}
                >
                  Edit
                </button>
                <button
                  className="btn btn-sm btn-danger"
                  onClick={() => handleDelete(c.id)}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Country;
