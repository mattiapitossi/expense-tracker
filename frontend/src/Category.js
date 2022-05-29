import React, { Component } from "react";
import { Table, Container, Form, FormGroup, Input, Label, Button } from "reactstrap";
import { Link } from "react-router-dom";
import AppNav from "./AppNav";

class Category extends Component {

  emptyItem = {
    id : "",
    name : ""
  }

  constructor(props) {
    super(props)

    this.state = {
      isLoading: true,
      Categories: [],
      item: this.emptyItem
    }

    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleChange = this.handleChange.bind(this);
  }

  async componentDidMount() {
    const response = await fetch("/api/category");
    const body = await response.json();
    this.setState({ Categories: body, isLoading: false });
  }

  
  async handleSubmit(event) {
    
    const item = this.state.item;
    
    
    await fetch(`/api/category`, {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
    });
    
    event.preventDefault();
    this.props.history.push("/category");
  }
  
  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;

    let item = { ...this.state.item };
    item[name] = value;
    this.setState({ item });

    console.log(this.state);
  }

  render() {

    const { Categories, isLoading } = this.state;
    if (isLoading) return <div>Loading...</div>;

    var styles = `
    .styled-table {
      border-collapse: collapse;
      margin: 25px 0;
      font-size: 0.9em;
      font-family: sans-serif;
      min-width: 400px;
      box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
  }

  .styled-table thead tr {
    background-color: #4682B4;
    color: #ffffff;
    text-align: left;
}

.styled-table th,
.styled-table td {
    padding: 12px 15px;
}

.styled-table tbody tr {
  border-bottom: 1px solid #dddddd;
}

.styled-table tbody tr:nth-of-type(even) {
  background-color: #f3f3f3;
}

.styled-table tbody tr:last-of-type {
  border-bottom: 2px solid #4682B4;
}

.styled-table tbody tr.active-row {
  font-weight: bold;
  color: #4682B4;
}
`

    var styleSheet = document.createElement("style")
    styleSheet.innerText = styles
    document.head.appendChild(styleSheet)

    let rows =
    Categories.map((category) => (
      <tr key={category.id}>
        <td>{category.name}</td>
      </tr>
    ))

    return (
      <div>
        <AppNav />
        <Container>
        <h2>Categories</h2>

        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="name">Name</Label>
            <Input type="name" name="name" id="name" onChange={this.handleChange} autoComplete="name" />
          </FormGroup>

          <FormGroup>
              <Button color="primary" type="submit">Save</Button>{' '}
              <Button color="secondary" tag={Link} to="/category">Cancel</Button>{' '}
            </FormGroup>
        </Form>

        
        </Container>

        {''}
        <Container>
          <h3>Expense List</h3>

          <table class="styled-table">
            <tbody>
              <tr class="active-row">
                <th>Name</th>
                <th>Action</th>
              </tr>
            </tbody>

            <tbody>
              {rows}
            </tbody>

          </table>
        </Container>
      </div>
    );
  }
}

export default Category;