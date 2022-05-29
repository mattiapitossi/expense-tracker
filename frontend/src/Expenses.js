import React, { Component } from "react";
import { Table, Container, Form, FormGroup, Input, Label, Button } from "reactstrap";
import DatePicker from "react-datepicker";
import 'react-datepicker/dist/react-datepicker.css';
import { Link } from "react-router-dom";
import AppNav from "./AppNav";
import Moment from "react-moment";

class Expenses extends Component {

  emptyItem = {
    description: "",
    expense_date: new Date(),
    location: "",
    value: 0,
    typeOfPayment: "CARD",
    typeOfTransaction: "OUT",
    category: {
      id: 2,
      name: "Utilities"
    },
    user: {
      id: 1,
      username: "admin",
      password: "1234"
    }
  }

  constructor(props) {
    super(props)

    this.state = {
      date: new Date(),
      isLoading: false,
      Categories: [],
      Expenses: [],
      item: this.emptyItem
    }

    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleDateChange = this.handleDateChange.bind(this);
  }

  async handleSubmit(event) {

    const item = this.state.item;


    await fetch(`/api/expenses`, {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
    });

    event.preventDefault();
    this.props.history.push("/expenses");
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

  handleDateChange(date) {
    let item = { ...this.state.item };
    item.expensedate = date;
    this.setState({ item });
  }

  async remove(id) {
    await fetch(`/api/expenses/${id}`, {
      method: "DELETE",
      headers: {
        "Accept": "application",
        "Content-Type": "application/json"
      }

    }).then(() => {
      let updateExpenses = [...this.state.Expenses].filter(i => i.id !== id);
      this.setState({ Expenses: updateExpenses })
    });

  }

  async componentDidMount() {
    const response = await fetch('/api/category');
    const body = await response.json();
    this.setState({ Categories: body, isLoading: false });

    const responseExp = await fetch('/api/expenses');
    const bodyExp = await responseExp.json();
    this.setState({ Expenses: bodyExp, isLoading: false });
  }

  render() {

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

    const title = <h3>Add Expense</h3>
    const { Categories } = this.state;
    const { Expenses, isLoading } = this.state;

    if (isLoading)
      return (<div>Loading...</div>)

    let optionList =
      Categories.map(category =>
        <option value={category.id} key={category.id}>
          {category.name}
        </option>
      )

    let rows =
      Expenses.map(expense =>
        <tr key={expense.id}>
          <td>{expense.title}</td>
          <td>{expense.description}</td>
          <td>{expense.location}</td>
          <td><Moment date={expense.expensedate} format="YYYY/MM/DDThh:mm" /></td>
          <td>{expense.typeOfPayment}</td>
          <td>{expense.typeOfTransaction}</td>
          <td>{expense.category.name}</td>
          <td>{expense.value} €</td>
          <td><Button size="sm" color="danger" onClick={() => this.remove(expense.id)}>Delete</Button></td>
        </tr>
      )

    return (
      <div>
        <AppNav />

        <Container>
          {title}

          <Form onSubmit={this.handleSubmit}>
            <FormGroup>
              <Label for="title">Title</Label>
              <Input type="title" name="title" id="title" onChange={this.handleChange} autoComplete="name"/>
            </FormGroup>

            <FormGroup>
              <Label for="description">Description</Label>
              <Input type="description" name="description" id="description" onChange={this.handleChange} autoComplete="name" />
            </FormGroup>

            <FormGroup>
              <Label for="category" >Category</Label>
              <select onChange={this.handleChange} name="category" id="category">
                {optionList}
              </select>
            </FormGroup>

            <FormGroup>
              <Label for="date">Expense Date</Label>
              <DatePicker selected={this.state.item.expense_date} onChange={this.handleDateChange} />
            </FormGroup>

            <FormGroup>
              <Label for="location">Location</Label>
              <Input type="text" name="location" id="location" onChange={this.handleChange} />
            </FormGroup>

            <FormGroup>
              <Label for="typeOfTransaction">Type of Transaction</Label>
              <select onChange={this.handleChange} name="typeOfTransaction" id="typeOfTransaction">
                <option>Out</option>
                <option>In</option>
                <option>InOut</option>
                <option>OutIn</option>
              </select>
            </FormGroup>

            <FormGroup>
              <Label for="typeOfPayment">Type of Payment</Label>
              <select onChange={this.handleChange} id="typeOfPayment" name="typeOfPayment">
                <option>Card</option>
                <option>Cash</option>
              </select>
            </FormGroup>

            <FormGroup>
              <Label for="value">Value</Label>
              <Input type="number" step="0.01" name="value" id="value" onChange={this.handleChange} />
            </FormGroup>

            <FormGroup>
              <Button color="primary" type="submit">Save</Button>{' '}
              <Button color="secondary" tag={Link} to="/categories">Cancel</Button>{' '}
            </FormGroup>
          </Form>
        </Container>

        {''}
        <Container>
          <h3>Expense List</h3>

          <table class="styled-table">
            <tbody>
              <tr class="active-row">
                <th width="10%%">Title</th>
                <th width="20%">Description</th>
                <th width="10%">Location</th>
                <th width="15%">Date</th>
                <th width="10%">Type Of Payment</th>
                <th width="10%">Type Of Transaction</th>
                <th>Category</th>
                <th>Value</th>
                <th width="10%">Action</th>
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

export default Expenses;
