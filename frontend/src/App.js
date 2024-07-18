import React, { useState, useEffect } from "react";
import { Routes, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSignIn } from "@fortawesome/free-solid-svg-icons";
import { faUserPlus } from "@fortawesome/free-solid-svg-icons";
import { faUser } from "@fortawesome/free-solid-svg-icons";
import { faBook } from "@fortawesome/free-solid-svg-icons";
import AuthService from "./services/auth.service";

import Login from "./components/Login";
import Register from "./components/Register";
import Profile from "./components/Profile";

import BoardAdmin from "./components/BoardAdmin";

import EventBus from "./common/EventBus";

import DeleteFeature from "./components/DeleteFeature";
import DeleteParameter from "./components/DeleteParamater";
import DeleteProduct from "./components/DeleteProduct";

import ViewAllProducts from "./components/ViewAllProducts";
import UpdateProductForm from "./components/UpdateProductForm";
import UserBoard from "./components/UserBoard";
import ProductByName from "./components/ProductByName";
import BoardManager from "./components/BoardManager";
import ViewAllproductsuser from "./components/ViewAllproductsuser";
import ViewAllProductsMgr from "./components/ViewallProductsMgr";
import ViewProductByIdMgr from "./components/ViewProductByIdMgr";
import HomePage from "./components/Homepage";
import ViewProductsByIdadmin from "./components/ViewProductsByIdadmin";
import AddQuotation from "./components/AddQuotation";
import ProductByNameadmin from "./components/ProductByNameadmin";
import ProductByNamemgr from "./components/ProductByNamemgr";
import UpdateRole from "./components/UpdateRole";
import AddProductAndFeatures from "./components/AddProductandFeatures";
import ViewAllQuotation from "./components/ViewAllQuotations";
import UploadJson from "./components/UploadJson";
import About from "./components/About";

const App = () => {
  const [showManagerBoard, setShowManagerBoard] = useState(false);
  const [showAdminBoard, setShowAdminBoard] = useState(false);
  const [showUserBoard, setShowUserBoard] = useState(false);
  const [currentUser, setCurrentUser] = useState(undefined);

  useEffect(() => {
    const user = AuthService.getCurrentUser();

    if (user) {
      setShowUserBoard(user.role.includes("ROLE_CUSTOMER"));
      setCurrentUser(user);
      setShowManagerBoard(user.role.includes("ROLE_MANAGER"));
      setCurrentUser(user);
      setShowAdminBoard(user.role.includes("ROLE_ADMIN"));
      setCurrentUser(user);
    }

    EventBus.on("logout", () => {
      logOut();
    });

    return () => {
      EventBus.remove("logout");
    };
  }, []);

  const logOut = () => {
    AuthService.logout();
    setShowUserBoard(false);
    setShowManagerBoard(false);
    setShowAdminBoard(false);
    setCurrentUser(undefined);
  };

  return (
    <div>
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <Link
          to={"/home"}
          className="navbar-brand"
          style={{ fontWeight: "bold" }}
        >
          <FontAwesomeIcon
            icon={faBook}
            className="me-1"
            style={{ fontWeight: "bold" }}
          />
          CMS
        </Link>
        <button
          className="navbar-toggler"
          type="button"
          data-toggle="collapse"
          data-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0">
            {/* Home link */}
            <li className="nav-item">
              <Link to={"/home"} className="nav-link">
                Home
              </Link>
            </li>

            <li>
              <Link to={"/view-all-products"} className="nav-link">
                Products
              </Link>
            </li>

            <li>
              <Link to={"/about"} className="nav-link">
                About
              </Link>
            </li>

            {/* Manager Dashboard link (conditionally rendered based on showManagerBoard) */}
            <li className="nav-item">
              {showManagerBoard && (
                <Link to={"/manager"} className="nav-link">
                  Manager Dashboard
                </Link>
              )}
            </li>

            {/* Admin Dashboard link (conditionally rendered based on showAdminBoard) */}
            <li className="nav-item">
              {showAdminBoard && (
                <Link to={"/admin"} className="nav-link">
                  Admin Dashboard
                </Link>
              )}
            </li>

            {/* User link (conditionally rendered based on showUserBoard) */}
            <li className="nav-item">
              {showUserBoard && (
                <Link to={"/user"} className="nav-link">
                  User
                </Link>
              )}
            </li>
          </ul>

          {/* Right-aligned section for login/signup or user profile/logout */}
          <div className="navbar-nav ml-auto">
            {currentUser ? (
              // If user is logged in
              <>
                <Link to="/profile" className="nav-link">
                  <FontAwesomeIcon icon={faUser} className="me-1" />
                  Profile
                </Link>
                <Link to={"/login"} className="nav-link" onClick={logOut}>
                  Logout
                </Link>
              </>
            ) : (
              // If user is not logged in
              <>
                <Link
                  to={"/login"}
                  className="nav-link btn btn-outline-custom ms-2 me-20"
                >
                  <FontAwesomeIcon icon={faSignIn} className="me-1" />
                  Login
                </Link>

                <span className="mx-2"></span>

                <Link
                  to={"/register"}
                  className="nav-link btn btn-outline-custom ms-2"
                >
                  <FontAwesomeIcon icon={faUserPlus} className="me-1" />
                  Register
                </Link>
              </>
            )}
          </div>
        </div>
      </nav>

      <div className="container-links">
        <Routes>
          <Route path="/" element={<ViewAllProducts />} />
          <Route path="/home" element={<HomePage />} />
          <Route path="/login" element={<Login />} />
          <Route path="/about" element={<About />} />
          <Route path="/register" element={<Register />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/admin" element={<BoardAdmin />} />
          {/* <Route path="/add-product" element={<AddProduct />} />
          <Route path="/add-features" element={<AddFeatures />} /> */}
          <Route path="/delete-param" element={<DeleteParameter />} />
          <Route path="/delete-feature" element={<DeleteFeature />} />
          <Route path="/delete-product" element={<DeleteProduct />} />
          <Route path="/view-product" element={<ViewAllProducts />} />
          <Route
            path="/view-productbyid-admin"
            element={<ViewProductsByIdadmin />}
          />
          <Route path="/update-product" element={<UpdateProductForm />} />
          <Route path="/user" element={<UserBoard />} />
          <Route path="/view-all-products" element={<ViewAllproductsuser />} />
          <Route path="/view-products-by-name" element={<ProductByName />} />
          <Route
            path="/view-productbyname-admin"
            element={<ProductByNameadmin />}
          />
          <Route
            path="/view-product-by-name-mgr"
            element={<ProductByNamemgr />}
          />
          <Route path="/manager" element={<BoardManager />} />
          <Route
            path="/view-all-products-mgr"
            element={<ViewAllProductsMgr />}
          />
          <Route
            path="/view-product-by-id-mgr"
            element={<ViewProductByIdMgr />}
          />
          <Route path="/create-quotation" element={<AddQuotation />} />
          <Route path="/get-quotation" element={<ViewAllQuotation />} />
          <Route path="/updaterole" element={<UpdateRole />} />
          <Route path="/add-products" element={<AddProductAndFeatures />} />
          <Route path="/json-upload" element={<UploadJson />} />
        </Routes>
      </div>
    </div>
  );
};

export default App;
