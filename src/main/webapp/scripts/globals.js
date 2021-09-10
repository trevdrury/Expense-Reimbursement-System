var domain = `http://localhost:9000`;

function getReimbType(typeId) {
  switch (typeId) {
    case 1:
      return "LODGING: ";
    case 2:
      return "TRAVEL: ";
    case 3:
      return "FOOD: ";
    case 4:
      return "OTHER: ";
  }
}

function getStatus(statusId) {
  switch (statusId) {
    case 1:
      return "Approved";
    case 2:
      return "Denied";
    case 3:
      return "Pending";
  }
}
