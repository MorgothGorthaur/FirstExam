const ManufacturerItem = ({manufacturer}) => {
    return (
        <div>
            <h1>{manufacturer.name}</h1>
            <h1>{manufacturer.country}</h1>
        </div>
    )
};
export default ManufacturerItem;