import React from "react";

export default class Book extends React.Component {

    // handleEditBook = () => {
    //     this.props.handleEditBook(this.props.book);
    // };

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
                        {/*<button onClick={this.handleEditBook} className="btn btn-outline-info btn-sm mr-2" type="button">*/}
                        {/*    edit*/}
                        {/*</button>*/}
                        <button onClick={this.handleDeleteBook} className="btn btn-outline-danger btn-sm" type="button">
                            delete
                        </button>
                    </div>
                </td>
            </tr>
        )
    }
}