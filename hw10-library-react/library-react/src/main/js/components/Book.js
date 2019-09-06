import React from "react";
import FormUpdateBook from "./FormUpdateBook";

export default class Book extends React.Component {

    handleDeleteBook = () => {
        window.confirm("Are you sure you wish to delete the book?") &&
        this.props.handleDeleteBook(this.props.book);
    };

    render() {
        return (
            <tr>
                <td>{this.props.book.isbn}</td>
                <td>{this.props.book.title}</td>
                <td>{this.props.book.author.lastName + ' ' + this.props.book.author.firstName}</td>
                <td>{this.props.book.genre.name}</td>
                <td>{this.props.book.publishYear}</td>
                <td>
                    <div className="input-group">
                        <FormUpdateBook attributes={this.props.attributes}
                                        book={this.props.book}
                                        handleUpdateBook={this.props.handleUpdateBook}/>
                        <button onClick={this.handleDeleteBook} className="btn btn-outline-danger btn-sm" type="button">
                            delete
                        </button>
                    </div>
                </td>
            </tr>
        )
    }
}