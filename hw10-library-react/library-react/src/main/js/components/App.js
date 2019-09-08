import React from "react";
import BookList from "./BookList";
import Header from "./Header";
import FormCreateBook from "./FormCreateBook";
import swal from "sweetalert";

const root = '/api/v1';

export default class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isLoaded: false,
            attributes: [],
            books: [],
            authors: [],
            genres: []
        };
    }

    handleUpdateBook = (book) => {
        this.callApi("/books/update/", {
                method: 'PUT',
                body: JSON.stringify(book)
            }, () => this.loadListBookFromServer()
        )
    };

    handleDeleteBook = (book) => {
        this.callApi("/books/delete/", {
                method: 'DELETE',
                body: book.isbn
            }, () =>
                this.loadListBookFromServer()
                    .then(() => swal("Deleted!", "Your book has been deleted!", "success"))
        )
    };

    handleCreateBook = (newBook) => {
        this.callApi("/books/add/", {
                method: 'POST',
                body: JSON.stringify(newBook)
            }, () => this.loadListBookFromServer()
        );
    };

    callApi = (url, init, successCallback) => {
        this.setState({isLoaded: false});
        fetch(root + url, {
            method: init.method ? init.method : 'GET',
            body: init.body ? init.body : null,
            headers: init.headers ? init.headers : {'Content-Type': 'application/json'}
        })
            .then(this.handleHttpErrors)
            .then((response) => {
                return successCallback ? successCallback(response) : this.setState({isLoaded: true})
            })
            .then(() => this.setState({isLoaded: true}))
            .catch(this.handleErrors)
    };

    handleErrors = (error) => {
        this.setState({isLoaded: true});
        error.response ?
            swal("Oops!", "Error code: " + error.response.status + "\n" + error.response.statusText, "error") :
            swal("Oops!", "Error: " + error, "error")
    };

    handleHttpErrors = (response) => {
        if (!response.ok) {
            throw new MyHttpError(response);
        }
        return response;
    };

    loadListBookFromServer = () => {
        return new Promise((resolve, reject) =>
            Promise.all([
                fetch(root + '/books'),
                fetch(root + '/books/attributes/')
            ])
                .then(([res1, res2]) => Promise.all([res1.json(), res2.json()]))
                .then(([books, bookDto]) =>
                    this.setState({
                        books,
                        attributes: Object.keys(bookDto).filter(key => key !== 'id'),
                    }))
                .then(() => resolve())
                .catch((reason) => reject(reason))
        )
    };

    loadAuthorsAndGenresFromServer = () => {
        return new Promise((resolve, reject) =>
            Promise.all([
                fetch(root + '/authors'),
                fetch(root + '/genres')
            ])
                .then(([res1, res2]) => Promise.all([res1.json(), res2.json()]))
                .then(([authors, genres]) => {
                    this.setState({
                        authors,
                        genres,
                    });
                }).then(() => resolve())
                .catch((reason) => reject(reason))
        )
    };

    // todo fix
    getProperties = (attribute) => {
        let properties = {};
        switch (attribute) {
            case 'id':
                properties[attribute] = {
                    title: attribute,
                    type: "number",
                    readOnly: true
                };
                break;
            case 'isbn':
                properties[attribute] = {
                    title: attribute,
                    type: "string",
                    readOnly: true
                };
                break;
            case 'title':
                properties[attribute] = {
                    title: attribute,
                    type: "string",
                    readOnly: false
                };
                break;
            case 'publishYear':
                properties[attribute] = {
                    title: attribute,
                    type: "number",
                    readOnly: false
                };
                break;
            case 'author':
                properties[attribute] = {
                    title: attribute,
                    type: "object",
                    readOnly: false
                };
                break;
            case 'genre':
                properties[attribute] = {
                    title: attribute,
                    type: "object",
                    readOnly: false
                };
                break;
        }
        return properties;
    };

    componentDidMount() {
        Promise.all([
            this.loadListBookFromServer(),
            this.loadAuthorsAndGenresFromServer()
        ]).then(() => this.setState({isLoaded: true}),
            (reason) => console.log(reason)
        );
    }

    render() {
        if (!this.state.isLoaded) {
            return <div>Загрузка...</div>;
        }

        return (
            <React.Fragment>
                <Header title={'Library'}/>
                <div className="container">
                    <div className="starter-template">
                        <BookList books={this.state.books}
                                  authors={this.state.authors}
                                  genres={this.state.genres}
                                  attributes={this.state.attributes}
                                  handleDeleteBook={this.handleDeleteBook}
                                  handleUpdateBook={this.handleUpdateBook}/>

                        <FormCreateBook attributes={this.state.attributes}
                                        authors={this.state.authors}
                                        genres={this.state.genres}
                                        handleCreateBook={this.handleCreateBook}/>
                    </div>
                </div>
            </React.Fragment>
        )
    }
}

function MyHttpError(response) {
    this.name = "HttpError";
    this.response = response;

    this.message = "Error code: " + response.status;

    if (Error.captureStackTrace) {
        Error.captureStackTrace(this, this.constructor);
    } else {
        this.stack = (new Error()).stack;
    }
}

MyHttpError.prototype = Object.create(Error.prototype);
MyHttpError.prototype.constructor = MyHttpError;