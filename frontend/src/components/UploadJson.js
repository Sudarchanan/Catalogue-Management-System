import React, { useState } from "react";
import UserService from "../services/userService";

const UploadJson = () => {
  const [file, setFile] = useState(null);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const handleFileChange = (event) => {
    const selectedFile = event.target.files[0];
    if (selectedFile && selectedFile.type === "application/json") {
      setFile(selectedFile);
      setMessage("");
      setError("");
    } else {
      setFile(null);
      setMessage("");
      setError("Please upload a valid JSON file.");
    }
  };

  const handleUpload = async () => {
    if (!file) {
      setError("No file selected. Please select a JSON file.");
      return;
    }

    try {
      await UserService.uploadFile(file);
      setMessage("File uploaded successfully.");
      setError("");
    } catch (error) {
      setError("Error uploading file: " + error.message);
      setMessage("");
    }
  };

  return (
    <div className="container mt-4">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card">
            <div className="card-body">
              <h2 className="text-center mb-4 font-weight-bold">
                Upload Product Details from JSON File
              </h2>
              <div className="mb-3 text-center">
                <input
                  type="file"
                  className="form-control-file"
                  onChange={handleFileChange}
                  style={{ width: "100%" }}
                />
              </div>
              <div className="text-center">
                <button className="btn btn-primary" onClick={handleUpload}>
                  Upload
                </button>
              </div>
              {message && <p className="text-success mt-3">{message}</p>}
              {error && <p className="text-danger mt-3">{error}</p>}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UploadJson;
