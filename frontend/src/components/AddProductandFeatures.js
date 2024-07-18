import React, { useState } from "react";
import UserService from "../services/userService";

const AddProductAndFeatures = () => {
  // State for adding a product and features
  const [productName, setProductName] = useState("");
  const [featureName, setFeatureName] = useState("");
  const [parameterName1, setParameterName1] = useState("");
  const [parameterType1, setParameterType1] = useState("");
  const [parameterValue1, setParameterValue1] = useState("");
  const [parameterName2, setParameterName2] = useState("");
  const [parameterType2, setParameterType2] = useState("");
  const [parameterValue2, setParameterValue2] = useState("");
  const [parameterName3, setParameterName3] = useState("");
  const [parameterType3, setParameterType3] = useState("");
  const [parameterValue3, setParameterValue3] = useState("");
  const [addedProduct, setAddedProduct] = useState(null);
  const [addedFeatures, setAddedFeatures] = useState(null);
  const [error, setError] = useState(null);

  // Function to handle adding a product and features
  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // Add the product first
      const productResponse = await UserService.addproduct({
        id: 0,
        name: productName,
        features: null,
      });
      const productId = productResponse.data.id;

      // Add the features with the product ID
      const featurePayload = {
        id: 0,
        name: featureName,
        product: {
          id: productId,
          name: productName,
        },
        parameters: [
          {
            id: 0,
            name: parameterName1,
            type: parameterType1,
            value: parameterValue1,
          },
          {
            id: 0,
            name: parameterName2,
            type: parameterType2,
            value: parameterValue2,
          },
          {
            id: 0,
            name: parameterName3,
            type: parameterType3,
            value: parameterValue3,
          },
        ],
      };
      const featureResponse = await UserService.addFeatures(featurePayload);

      // Update the state with the added product and features
      setAddedProduct(productResponse.data);
      setAddedFeatures(featureResponse.data);

      // Reset the form
      setProductName("");
      setFeatureName("");
      setParameterName1("");
      setParameterType1("");
      setParameterValue1("");
      setParameterName2("");
      setParameterType2("");
      setParameterValue2("");
      setParameterName3("");
      setParameterType3("");
      setParameterValue3("");
      setError(null);
    } catch (error) {
      console.error("Error adding product and features:", error);
      setError("Error adding product and features");
    }
  };

  return (
    <div className="container">
      <div className="row justify-content-center">
        <div className="col-md-8">
          <div className="card p-4">
            <h2 className="mt-4 text-center">Add Product and Features</h2>
            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label htmlFor="productName" className="form-label">
                  Product Name:
                </label>
                <input
                  type="text"
                  id="productName"
                  className="form-control"
                  value={productName}
                  onChange={(e) => setProductName(e.target.value)}
                  required
                />
              </div>
              <div className="mb-3">
                <label htmlFor="featureName" className="form-label">
                  Feature Name:
                </label>
                <input
                  type="text"
                  id="featureName"
                  className="form-control"
                  value={featureName}
                  onChange={(e) => setFeatureName(e.target.value)}
                  required
                />
              </div>
              {/* Parameters for Features */}
              <div className="mb-3">
                <label htmlFor="parameterName1" className="form-label">
                  Parameter Name 1:
                </label>
                <input
                  type="text"
                  id="parameterName1"
                  className="form-control"
                  value={parameterName1}
                  onChange={(e) => setParameterName1(e.target.value)}
                  required
                />
              </div>
              <div className="mb-3">
                <label htmlFor="parameterType1" className="form-label">
                  Parameter Type 1:
                </label>
                <input
                  type="text"
                  id="parameterType1"
                  className="form-control"
                  value={parameterType1}
                  onChange={(e) => setParameterType1(e.target.value)}
                  required
                />
              </div>
              <div className="mb-3">
                <label htmlFor="parameterValue1" className="form-label">
                  Parameter Value 1:
                </label>
                <input
                  type="text"
                  id="parameterValue1"
                  className="form-control"
                  value={parameterValue1}
                  onChange={(e) => setParameterValue1(e.target.value)}
                  required
                />
              </div>
              <div className="mb-3">
                <label htmlFor="parameterName2" className="form-label">
                  Parameter Name 2:
                </label>
                <input
                  type="text"
                  id="parameterName2"
                  className="form-control"
                  value={parameterName2}
                  onChange={(e) => setParameterName2(e.target.value)}
                  required
                />
              </div>
              <div className="mb-3">
                <label htmlFor="parameterType2" className="form-label">
                  Parameter Type 2:
                </label>
                <input
                  type="text"
                  id="parameterType2"
                  className="form-control"
                  value={parameterType2}
                  onChange={(e) => setParameterType2(e.target.value)}
                  required
                />
              </div>
              <div className="mb-3">
                <label htmlFor="parameterValue2" className="form-label">
                  Parameter Value 2:
                </label>
                <input
                  type="text"
                  id="parameterValue2"
                  className="form-control"
                  value={parameterValue2}
                  onChange={(e) => setParameterValue2(e.target.value)}
                  required
                />
              </div>
              <div className="mb-3">
                <label htmlFor="parameterName3" className="form-label">
                  Parameter Name 3:
                </label>
                <input
                  type="text"
                  id="parameterName3"
                  className="form-control"
                  value={parameterName3}
                  onChange={(e) => setParameterName3(e.target.value)}
                  required
                />
              </div>
              <div className="mb-3">
                <label htmlFor="parameterType3" className="form-label">
                  Parameter Type 3:
                </label>
                <input
                  type="text"
                  id="parameterType3"
                  className="form-control"
                  value={parameterType3}
                  onChange={(e) => setParameterType3(e.target.value)}
                  required
                />
              </div>
              <div className="mb-3">
                <label htmlFor="parameterValue3" className="form-label">
                  Parameter Value 3:
                </label>
                <input
                  type="text"
                  id="parameterValue3"
                  className="form-control"
                  value={parameterValue3}
                  onChange={(e) => setParameterValue3(e.target.value)}
                  required
                />
              </div>
              <button type="submit" className="btn btn-primary btn-sm">
                Add Product and Features
              </button>
            </form>
            {addedProduct && (
              <div className="mt-4">
                <h3>Product added successfully:</h3>
                <p>Name: {addedProduct.name}</p>
              </div>
            )}
            {addedFeatures && (
              <div className="mt-4">
                <h3>Features added successfully:</h3>
                <p>Feature ID: {addedFeatures.id}</p>
                <p>Feature Name: {addedFeatures.name}</p>
              </div>
            )}
            {error && <p className="mt-4 text-danger">Error: {error}</p>}
          </div>
        </div>
      </div>
    </div>
  );
};

export default AddProductAndFeatures;
