import React, { Component } from "react";
import { Container, Form, FormGroup } from "reactstrap";
import AppNav from "./AppNav";

class Expenses extends Component {
  state = {};
  render() {
    return (
      <div>
        <AppNav />
        <Container>
        <h2>Expenses</h2>
          <Form>
            <FormGroup>
              <label for = "title">Title</label>
              <input type = "text" name = "title" id = "title" onChange={this.handleChange}/>
            </FormGroup>

            <FormGroup>
              <label for = "category">Category</label>
              <input type = "text" name = "category" id = "category" onChange={this.handleChange}/>
            </FormGroup>

            <FormGroup>
              <label for = "date">Expense Date</label>
              <input type = "text" name = "date" id = "date" onChange={this.handleChange}/>
            </FormGroup>

            <FormGroup>
              <label for = "location">Location</label>
              <input type = "text" name = "location" id = "location" onChange={this.handleChange}/>
            </FormGroup>

            <FormGroup>
              <Button color = "primary" type = "submit">Save</Button>{' '}
              <Button color = "secondary" tag = {Link} to = "/categories">Cancel</Button>{' '}
            </FormGroup>

          </Form>
        </Container>
      </div>
    );
  }
}

export default Expenses;
