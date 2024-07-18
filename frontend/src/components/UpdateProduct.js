import React, { useState, useEffect } from "react";

const UpdateProduct = ({ product, onProductUpdate, products }) => {
  const [updatedProduct, setUpdatedProduct] = useState(null); // Initialize as null initially

  // Initialize the updatedProduct state when product prop changes
  useEffect(() => {
    if (product) {
      setUpdatedProduct({
        id: product.id,
        name: product.name,
        features: product.features.map((feature) => ({
          id: feature.id,
          name: feature.name,
          product: {
            id: feature.product ? feature.product.id : product.id,
            name: feature.product ? feature.product.name : product.name, // Set default to current product name
          },
          parameters: feature.parameters.map((param) => ({
            id: param.id,
            name: param.name,
            type: param.type,
            value: param.value,
          })),
        })),
      });
    }
  }, [product]);

  // Handler to update product name and ID
  const handleProductNameChange = (featureIndex, newName) => {
    // Find the selected product object from products array
    const selectedProduct = products.find((prod) => prod.name === newName);

    if (!selectedProduct) return; // Handle case where selected product is not found

    setUpdatedProduct((prevProduct) => ({
      ...prevProduct,
      features: prevProduct.features.map((feature, index) => {
        if (index !== featureIndex) return feature;

        return {
          ...feature,
          product: {
            id: selectedProduct.id,
            name: newName,
          },
        };
      }),
    }));
  };

  // Handler to update feature name
  const handleFeatureNameChange = (featureIndex, newValue) => {
    setUpdatedProduct((prevProduct) => ({
      ...prevProduct,
      features: prevProduct.features.map((feature, index) => {
        if (index !== featureIndex) return feature;
        return { ...feature, name: newValue };
      }),
    }));
  };

  // Handler to update parameter value
  const handleParameterChange = (featureIndex, parameterIndex, newValue) => {
    setUpdatedProduct((prevProduct) => ({
      ...prevProduct,
      features: prevProduct.features.map((feature, index) => {
        if (index !== featureIndex) return feature;
        return {
          ...feature,
          parameters: feature.parameters.map((param, pIndex) => {
            if (pIndex !== parameterIndex) return param;
            return { ...param, value: newValue };
          }),
        };
      }),
    }));
  };

  // Handle form submission
  const handleSubmit = (e) => {
    e.preventDefault();
    onProductUpdate(updatedProduct);
  };

  // Render loading or null if product is not yet fetched
  if (!updatedProduct) {
    return null; // or render loading indicator
  }

  return (
    <div className="container mt-4">
      <div className="row justify-content-center">
        <div className="col-lg-10">
          <div className="card p-4 rounded">
            <form onSubmit={handleSubmit}>
              <h3>Update Product</h3>
              <div className="mb-3">
                <label htmlFor="productName" className="form-label">
                  Product Name:
                </label>
                <input
                  type="text"
                  id="productName"
                  value={updatedProduct.name}
                  onChange={(e) =>
                    setUpdatedProduct((prevProduct) => ({
                      ...prevProduct,
                      name: e.target.value,
                    }))
                  }
                  className="form-control"
                  required
                />
              </div>

              {/* Horizontal line and updating features heading */}
              <hr />
              <h4>Updating Features</h4>

              {updatedProduct.features.map((feature, featureIndex) => (
                <div key={feature.id}>
                  <h5>Feature Name:</h5>
                  <input
                    type="text"
                    value={feature.name}
                    onChange={(e) =>
                      handleFeatureNameChange(featureIndex, e.target.value)
                    }
                    className="form-control mb-3"
                    required
                  />
                  <h5>Product:</h5>
                  <select
                    value={feature.product.name}
                    onChange={(e) =>
                      handleProductNameChange(featureIndex, e.target.value)
                    }
                    className="form-control mb-3"
                    required
                  >
                    {products.map((prod) => (
                      <option key={prod.id} value={prod.name}>
                        {prod.name}
                      </option>
                    ))}
                  </select>
                  {feature.parameters.map((param, parameterIndex) => (
                    <div key={param.id}>
                      <label
                        htmlFor={`param-${featureIndex}-${parameterIndex}`}
                        className="form-label"
                      >
                        {param.name}:
                      </label>
                      <input
                        type="text"
                        id={`param-${featureIndex}-${parameterIndex}`}
                        value={param.value}
                        onChange={(e) =>
                          handleParameterChange(
                            featureIndex,
                            parameterIndex,
                            e.target.value
                          )
                        }
                        className="form-control mb-3"
                        required
                      />
                    </div>
                  ))}
                </div>
              ))}
              <button type="submit" className="btn btn-primary mt-3">
                Update Product
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UpdateProduct;
