import React from "react";
import * as ReactDOM from "react-dom";

const root = '/api/v1';

export default class FormCreateBook extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            genres: [],
            authors: [],
            optionAuthor: 0,
            optionGenre: 0
        }
    }

    handleSubmit = (event) => {
        event.preventDefault();

        const newBook = {};
        this.props.attributes.forEach(attribute => {
            if (attribute === 'author') {
                newBook[attribute] = this.state.authors[this.state.optionAuthor];
            } else if (attribute === 'genre') {
                newBook[attribute] = this.state.genres[this.state.optionGenre];
            } else {
                newBook[attribute] = ReactDOM.findDOMNode(this.refs[attribute]).value.trim();
            }
        });
        this.props.handleCreateBook(newBook);

        // clear out the dialog's inputs
        this.resetInputsForm();

        // Navigate away from the dialog to hide it.
        window.location = "#";
    };

    resetInputsForm = (event) => {
        event && event.preventDefault();

        this.props.attributes.forEach(attribute => {
            if (attribute === 'author') {
                this.state.optionAuthor = 0;
            } else if (attribute === 'genre') {
                this.state.optionGenre = 0;
            } else {
                ReactDOM.findDOMNode(this.refs[attribute]).value = '';
            }
        });
        //todo
        this.setState({});
    };

    onSelectAuthor = (event) => {
        this.setState({optionAuthor: event.target.value});
    };

    onSelectGenre = (event) => {
        this.setState({optionGenre: event.target.value});
    };

    getTagByAttribute = (attribute) => {
        let tag;
        if (attribute === 'author') {
            tag =
                <select value={this.state.optionAuthor} className="form-control" onChange={this.onSelectAuthor}>
                    {this.state.authors.map((author, index) =>
                        <option key={author.id} value={index}>{author.lastName + ' ' + author.firstName}</option>
                    )}
                </select>
        } else if (attribute === 'genre') {
            tag =
                <select value={this.state.optionGenre} className="form-control" onChange={this.onSelectGenre}>
                    {this.state.genres.map((genre, index) =>
                        <option key={genre.id} value={index}>{genre.name}</option>
                    )}
                </select>
        } else {
            tag =
                <input type="text" className="form-control" ref={attribute}
                       placeholder={attribute} required/>
        }
        return tag;
    };

    loadAuthorsAndGenresFromServer = () => {
        Promise.all([
            fetch(root + '/authors'),
            fetch(root + '/genres')
        ])
            .then(([res1, res2]) => Promise.all([res1.json(), res2.json()]))
            .then(([authors, genres]) => this.setState({authors, genres}));
    };

    componentDidMount() {
        this.loadAuthorsAndGenresFromServer();
    }

    render() {
        const inputs = this.props.attributes.map(attribute =>
            <div key={attribute} className="form-group">
                <label htmlFor={attribute}>{attribute}</label>
                {this.getTagByAttribute(attribute)}
            </div>
        );

        return (
            <div>
                <a className="btn btn-primary" role="button" href="#createBook">Add book</a>

                <div id="createBook" className="modalDialog">
                    <div>
                        <a href="#" title="Close" className="close">X</a>

                        <form>
                            <fieldset>
                                <legend>Add new book</legend>
                                {inputs}
                                <div className="form-group">
                                    <button onClick={this.handleSubmit} className="btn btn-primary mr-2" type="button">
                                        Create
                                    </button>
                                    <button onClick={this.resetInputsForm} className="btn btn-light" type="reset">
                                        Reset
                                    </button>
                                </div>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        )
    }

}