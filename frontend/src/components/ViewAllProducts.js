import React, { useState, useEffect } from "react";
import UserService from "../services/userService";

const ViewAllProducts = () => {
  const [products, setProducts] = useState([]);
  const [error, setError] = useState(null);

  // Function to fetch all products
  const getAllProducts = async () => {
    try {
      const response = await UserService.getall();
      setProducts(response.data);
    } catch (error) {
      console.error("Error fetching products:", error);
      setError("Error fetching products");
    }
  };

  useEffect(() => {
    // Fetch all products when component mounts
    getAllProducts();
  }, []);

  return (
    <div className="prod-container mt-4">
      <h2>All Products</h2>
      {error && <p className="mt-4 text-danger">Error: {error}</p>}
      <div className="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-4">
        {products.map((product) => (
          <div key={product.id} className="col mb-4">
            <div className="card h-100">
              <div className="card-header">
                <h3 className="card-title">Product: {product.name}</h3>
              </div>
              <div className="card-body">
                {product.features.map((feature) => (
                  <div key={feature.id} className="mb-3">
                    <h4>Feature: {feature.name}</h4>
                    <ul className="list-unstyled">
                      {feature.parameters.map((parameter) => (
                        <li key={parameter.id}>
                          {parameter.name}: {parameter.value} ({parameter.type})
                        </li>
                      ))}
                    </ul>
                  </div>
                ))}
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ViewAllProducts;
