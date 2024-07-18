import React, { useState, useEffect } from "react";

import UserService from "../services/userService";
import ViewAllproductsuser from "./ViewAllproductsuser";
import About from "./About";
import Footer from "./Footer";
import Intro from "./Intro";

const Homepage = () => {
  const [content, setContent] = useState("");

  useEffect(() => {
    UserService.getPublicContent().then(
      (response) => {
        setContent(response.data);
      },
      (error) => {
        const _content =
          (error.response && error.response.data) ||
          error.message ||
          error.toString();

        setContent(_content);
      }
    );
  }, []);

  return (
    <>
      <div className="container-home">
        <Intro />
        <header className="jumbotron mb-0">
          <ViewAllproductsuser />
        </header>
      </div>
      <About />
      <Footer />
    </>
  );
};

export default Homepage;
