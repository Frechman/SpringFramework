import React from "react";
import Book from "./Book";

export default class BookList extends React.Component {
    render() {
        const books = this.props.books.map(book =>
            <Book key={book.id} book={book}
                  // handleEditBook={this.props.handleEditBook}
                  handleDeleteBook={this.props.handleDeleteBook}/>
        );
        return (
            <div className="table-responsive">
                <table className="table table-bordered table-hover table-sm">
                    <thead>
                    <tr>
                        <th>Isbn</th>
                        <th>Title</th>
                        <th>Author</th>
                        <th>Genre</th>
                        <th>Publish year</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {books}
                    </tbody>
                </table>
            </div>
        )
    }
}
