import React, { useState, useEffect } from "react";
import UserService from "../services/userService";

const DeleteProduct = () => {
  const [products, setProducts] = useState([]);
  const [selectedProductId, setSelectedProductId] = useState("");
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);

  // Fetch products when component mounts and after successful deletion
  useEffect(() => {
    async function fetchProducts() {
      try {
        const response = await UserService.getall(); // Adjust based on your UserService implementation
        setProducts(response.data); // Assuming response contains an array of products
      } catch (error) {
        console.error("Error fetching products:", error);
      }
    }

    fetchProducts();
  }, [success]); // Include success in dependency array to refetch products after deletion

  // Function to handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await UserService.delProduct(selectedProductId);
      setSuccess(true); // Trigger useEffect to refetch products
      setError(null);
      setSelectedProductId(""); // Reset selected product after deletion
    } catch (error) {
      console.error("Error deleting product:", error);
      if (error.response && error.response.status === 404) {
        setError("Product not found.");
      } else if (error.response && error.response.status === 500) {
        setError("Product with quotations cannot be deleted.");
      } else {
        setError("Error deleting product");
      }
    }
  };

  // Function to handle dropdown change
  const handleDropdownChange = (e) => {
    setSelectedProductId(e.target.value);
  };

  return (
    <div className="container mt-4">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card p-4 rounded">
            <h2 className="mt-4">Delete Product</h2>
            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label htmlFor="productDropdown" className="form-label">
                  Select Product:
                </label>
                <select
                  id="productDropdown"
                  className="form-control"
                  value={selectedProductId}
                  onChange={handleDropdownChange}
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
              <button type="submit" className="btn btn-danger">
                Delete Product
              </button>
            </form>
            {success && (
              <div className="mt-4">
                <p className="text-success">Product deleted successfully.</p>
              </div>
            )}
            {error && <p className="mt-4 text-danger">Error: {error}</p>}
          </div>
        </div>
      </div>
    </div>
  );
};

export default DeleteProduct;
