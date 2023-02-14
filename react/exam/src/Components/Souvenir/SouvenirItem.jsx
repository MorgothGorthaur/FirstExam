import {Button, Form, Modal} from "react-bootstrap";
import {useState} from "react";
import SouvenirForm from "../SouvenirForm";

const SouvenirItem = ({souvenir, removeSouvenir, updateSouvenir}) => {
    const [modal, setModal] = useState(false);
    const update = (data) => {
        setModal(false);
        updateSouvenir(data);
    }
    return (
        <div>
            <h1> {souvenir.name}</h1>
            <h1> {souvenir.price}</h1>
            <h1> {souvenir.date}</h1>
            <Button onClick={() => setModal(true)}> update </Button>
            <Modal show={modal} onHide={setModal}> <SouvenirForm CreateOrUpdate={update} souvenir={souvenir} /> </Modal>
            <Button variant={"danger"} onClick={() => removeSouvenir(souvenir.id)} > remove </Button>
        </div>
    );
};
export default SouvenirItem;