import React, { useState, useEffect } from "react";
import UserService from "../services/userService";

const DeleteFeature = () => {
  const [productId, setProductId] = useState(""); // State for selected product ID
  const [featureId, setFeatureId] = useState(""); // State for selected feature ID
  const [products, setProducts] = useState([]); // State for all products
  const [features, setFeatures] = useState([]); // State for features of selected product
  const [error, setError] = useState(null); // State for error message
  const [success, setSuccess] = useState(false); // State for success message

  // Fetch all products on component mount
  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await UserService.getall(); // Fetch all products
        setProducts(response.data); // Assuming response contains an array of products
      } catch (error) {
        console.error("Error fetching products:", error);
        setError("Error fetching products. Please try again later.");
      }
    };

    fetchProducts();
  }, []);

  // Fetch features based on selected productId
  useEffect(() => {
    const fetchFeatures = async () => {
      try {
        if (productId) {
          const response = await UserService.getfeaturebyproductidadmin(
            productId
          ); // Fetch features by productId
          setFeatures(response.data); // Assuming response contains an array of features
        } else {
          setFeatures([]); // Reset features if no productId selected
        }
      } catch (error) {
        console.error("Error fetching features:", error);
        setError("Error fetching features. Please try again later.");
      }
    };

    fetchFeatures();
  }, [productId]); // Execute whenever productId changes

  // Handle form submission to delete feature
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await UserService.delFeatures(featureId); // Delete feature by featureId
      setSuccess(true); // Set success message
      setError(null); // Clear error message
      setFeatureId(""); // Reset selected featureId after deletion
      // Update features list after deletion
      const updatedFeatures = features.filter(
        (feat) => feat.id !== parseInt(featureId)
      );
      setFeatures(updatedFeatures);
    } catch (error) {
      console.error("Error deleting feature:", error);
      if (error.response && error.response.status === 404) {
        setError("Feature not found."); // Handle specific errors
      } else {
        setError("Error deleting feature."); // Generic error message
      }
    }
  };

  return (
    <div className="container mt-4">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card p-4 rounded">
            <h2 className="mb-4">Delete Feature</h2>
            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label htmlFor="productId">Product Name:</label>
                <select
                  className="form-control"
                  id="productId"
                  value={productId}
                  onChange={(e) => {
                    setProductId(e.target.value);
                    setFeatures([]); // Clear features when changing product
                  }}
                  required
                >
                  <option value="">-- Select Product --</option>
                  {products.map((product) => (
                    <option key={product.id} value={product.id}>
                      {product.name}
                    </option>
                  ))}
                </select>
              </div>
              <div className="form-group">
                <label htmlFor="featureId">Product Feature:</label>
                <select
                  className="form-control"
                  id="featureId"
                  value={featureId}
                  onChange={(e) => setFeatureId(e.target.value)}
                  required
                  disabled={!productId} // Disable if no productId selected
                >
                  <option value="">-- Select Feature --</option>
                  {features.map((feature) => (
                    <option key={feature.id} value={feature.id}>
                      {feature.name}
                    </option>
                  ))}
                </select>
              </div>
              <button type="submit" className="btn btn-danger">
                Delete Feature
              </button>
            </form>
            {success && (
              <p className="text-success mt-3">Feature deleted successfully.</p>
            )}
            {error && <p className="text-danger mt-3">Error: {error}</p>}
          </div>
        </div>
      </div>
    </div>
  );
};

export default DeleteFeature;
