let newBtn = document.getElementById("new-btn");
let formContainer = document.getElementById("form-container");
let overlay = document.getElementById("overlay");
let closeBtn = document.getElementById("close");
let reimbForm = document.getElementById("reimb-form");
let typeInput = document.getElementById("custom-select");
let typeDropdown = document.getElementById("type-dropdown");
let selection = document.getElementById("selection");
let reimbType;

// New Reimbursement button functionality
function newBtnHandler() {
  formContainer.style.left = "0";
  formContainer.style.paddingLeft = "5.6rem";
  formContainer.style.paddingRight = "5.6rem";
  overlay.style.display = "block";
}

newBtn.addEventListener("click", newBtnHandler);

// Sidebar Close button functionality
closeBtn.onclick = function () {
  formContainer.style.left = "-72rem";
  overlay.style.display = "none";
};

// New Reimbursement form functionality
typeInput.addEventListener("click", typeInputHandler);

function typeInputHandler() {
  typeDropdown.classList.toggle("hidden");
}

typeDropdown.addEventListener("click", typeDropdownHandler);

function typeDropdownHandler(e) {
  reimbType = e.target.dataset.id;

  selection.textContent = e.target.textContent;
}

reimbForm.onsubmit = async function (e) {
  e.preventDefault();

  let desc = document.getElementById("description").value;
  let amount = document.getElementById("amount").value;
  let author = userId;
  let typeId = reimbType;
  let formContainer = document.getElementById("form-container");
  let overlay = document.getElementById("overlay");
  let reimbRes;

  if ((desc.length == 0) | (amount.length == 0)) {
    alert("Please make sure all fields are complete");
    return false;
  } else {
    reimbRes = await fetch(`${domain}/api/reimbursements`, {
      method: "POST",
      body: JSON.stringify({
        amount: amount,
        description: desc,
        authorId: author,
        typeId: typeId,
      }),
    });
  }

  let reimbResData = await reimbRes.json();

  if (reimbResData.success) {
    desc.value = "";
    amount.value = "";

    formContainer.style.left = "-72rem";
    overlay.style.display = "none";

    reimbursements = await getReimbursements(userId);
    getDataForUser(reimbursements);
  }
};
