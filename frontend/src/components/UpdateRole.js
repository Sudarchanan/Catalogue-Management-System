import React, { useState, useEffect } from "react";
import UserService from "../services/userService";

const UpdateRole = () => {
  const [userId, setUserId] = useState(""); // State for user ID
  const [selectedRole, setSelectedRole] = useState(null); // State for selected role object
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [users, setUsers] = useState([]);
  const [roles, setRoles] = useState([]);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const response = await UserService.getalluser();
        setUsers(response.data);
      } catch (error) {
        console.error("Error fetching users:", error);
      }
    };

    const fetchRoles = async () => {
      try {
        const response = await UserService.getallrole();
        setRoles(response.data);
      } catch (error) {
        console.error("Error fetching roles:", error);
      }
    };

    fetchUsers();
    fetchRoles();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage("");
    setError("");

    try {
      const selectedUser = users.find((user) => user.id.toString() === userId);
      if (!selectedUser) {
        throw new Error("User not found.");
      }

      if (!selectedRole) {
        throw new Error("Please select a role.");
      }

      await UserService.updaterolebyuser(userId, selectedRole); // Pass entire selectedRole object
      setMessage("Role updated successfully!");
    } catch (error) {
      if (error.response && error.response.status === 404) {
        setError("User not found.");
      } else {
        setError("An error occurred while updating the role.");
      }
    }
  };

  const handleRoleChange = (e) => {
    const roleId = e.target.value;
    const role = roles.find((r) => r.id.toString() === roleId);
    setSelectedRole(role);
  };

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-lg-6">
          <div className="card p-4 rounded">
            <h2 className="mb-4">Update User Role</h2>
            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label>User Username:</label>
                <select
                  className="form-control"
                  value={userId}
                  onChange={(e) => setUserId(e.target.value)}
                  required
                >
                  <option value="">Select User</option>
                  {users.map((user) => (
                    <option key={user.id} value={user.id}>
                      {user.username}
                    </option>
                  ))}
                </select>
              </div>
              <div className="form-group">
                <label>Role:</label>
                <select
                  className="form-control"
                  value={selectedRole ? selectedRole.id : ""}
                  onChange={handleRoleChange}
                  required
                >
                  <option value="">Select Role</option>
                  {roles.map((role) => (
                    <option key={role.id} value={role.id}>
                      {role.name}
                    </option>
                  ))}
                </select>
              </div>
              <button type="submit" className="btn btn-primary">
                Update Role
              </button>
            </form>
            {message && <div className="mt-3 alert alert-info">{message}</div>}
            {error && <div className="mt-3 alert alert-danger">{error}</div>}
          </div>
        </div>
      </div>
    </div>
  );
};

export default UpdateRole;
