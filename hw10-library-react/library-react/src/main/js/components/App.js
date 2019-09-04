import React from "react";
import BookList from "./BookList";

const Header = (props) => (
    <h1>{props.title}</h1>
);

export default class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {books: []};
    }

    componentDidMount() {
        fetch('/books')
            .then(response => response.json())
            .then(books => this.setState({books}));
    }

    render() {
        return (
            <React.Fragment>
                <Header title={'Library'}/>
                <BookList books={this.state.books}/>
            </React.Fragment>
        )
    }
}