import {Button, Form, Modal} from "react-bootstrap";
import SouvenirForm from "../Manufacturer/SouvenirForm";
import {useState} from "react";

const SouvenirItem = ({souvenir}) => {
    const [modal,setModal] = useState(false);
    const [manufacturer, setManufacturer] = useState();
    return (
        <div>
            <h1> {souvenir.name}</h1>
            <h1> {souvenir.price}</h1>
            <h1> {souvenir.date}</h1>
            <Button onClick={setModal(true)} > open </Button>
        </div>
    );
};
export default SouvenirItem;