import React from "react";
import FormUpdateBook from "./FormUpdateBook";
import swal from 'sweetalert';

export default class Book extends React.Component {

    handleDeleteBook = () => {
        swal({
            title: "Are you sure?",
            text: "Are you sure that you want to delete this book?",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
            .then((willDelete) => {
                if (willDelete) {
                    this.props.handleDeleteBook(this.props.book);
                } else {
                    swal.close();
                }
            });
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
                                        authors={this.props.authors}
                                        genres={this.props.genres}
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