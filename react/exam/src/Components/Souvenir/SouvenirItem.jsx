import {useState} from "react";
import SouvenirService from "../../API/SouvenirService";
import {Button} from "react-bootstrap";

const SouvenirItem = ({souvenir}) => {
    const [modal, setModal] = useState(false);
    const [manufacturer, setManufacturer] = useState();
    const getManufacturer = () => {
        setModal(true);
        SouvenirService.getManufacturer(souvenir.id).then(data =>  setManufacturer(data));
    }
    return (
        <div>
            <h1> {souvenir.name}</h1>
            <h1> {souvenir.price}</h1>
            <h1> {souvenir.date}</h1>
            <div>
                {
                    modal ? (
                        <div>
                            <h1>{manufacturer.name}</h1>
                            <h1>{manufacturer.country}</h1>
                            <h1></h1>
                            <Button variant={"dark"} onClick={() => setModal(false)}>close</Button>
                        </div>
                    ) : (
                        <Button onClick={() => getManufacturer()}>open</Button>
                    )
                }
            </div>
        </div>
    );
};
export default SouvenirItem;