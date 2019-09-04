import React from "react";
import Book from "./Book";

export default class BookList extends React.Component {
    render() {
        const books = this.props.books.map(book =>
                <Book key={book.id} book={book}/>
        );
        return (
            <table>
                <tbody>
                <tr>
                    <th>Isbn</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Genre</th>
                    <th>Publish year</th>
                </tr>
                {books}
                </tbody>
            </table>
        )
    }
}
