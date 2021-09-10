const urlParams = new URLSearchParams(window.location.search);
const userRoleId = urlParams.get("role");
const username = urlParams.get("uName");
const userId = urlParams.get("id");
let reimbursements = [];

// Check session and redirect user to login if no session found
window.onload = async function () {
  const sessionRes = await fetch(`${domain}/api/check-session`);
  const sessionUserData = await sessionRes.json();

  console.log(sessionUserData);

  if (sessionUserData.data) {
    if (sessionUserData.data.username != username) {
      window.location = `${domain}/`;
    }
  } else {
    window.location = `${domain}/`;
  }

  reimbursements = await getReimbursements(userId);
  console.log(reimbursements);

  getDataForUser(reimbursements);
};

// fetch reimbursements
async function getReimbursements(userId) {
  let reimbRes;

  if (userRoleId == 1) {
    reimbRes = await fetch(`${domain}/api/reimbursements?id=${userId}`);
  } else {
    reimbRes = await fetch(`${domain}/api/reimbursements`);
  }

  let reimbData = await reimbRes.json();

  let reimbList = [];

  reimbData.data.forEach((reimb) => {
    reimbList.push(reimb);
  });

  return reimbList;
}

// gets data and adds it to the DOM
function getDataForUser(reimbList) {
  let userNameElem = document.getElementById("user");
  userNameElem.innerText =
    userRoleId == 1 ? `for ${username}` : `for all users`;

  let dashContainer = document.getElementById("dash-container");
  dashContainer.innerHTML = "";

  let empty = document.createElement("div");

  if (reimbList.length < 1) {
    empty.innerHTML = `
      <div class='empty'>
        <h2>There are currently no reimbursements to display.</h2>
        <img src='../assets/undraw_empty_xct9.svg'>
      </div>
    `;

    dashContainer.appendChild(empty);
  }

  reimbList.forEach((reimb) => {
    let submitted = new Date(reimb.timeSubmitted);
    submitted = submitted.toLocaleString("default", {
      day: "numeric",
      month: "short",
      year: "numeric",
    });

    let resolved =
      reimb.timeResolved === null ? "" : new Date(reimb.timeResolved);

    if (resolved != "") {
      resolved = resolved.toLocaleString("default", {
        day: "numeric",
        month: "short",
        year: "numeric",
      });
    }

    let status = getStatus(reimb.statusId);

    let reimbContainer = document.createElement("div");
    reimbContainer.classList.add("reimb-container");
    reimbContainer.innerHTML = `
      <div class="reimb-header">
        <div class="reimb-header-left">
          <p>${reimb.firstName} ${reimb.lastName}</p>
        </div>
        <div class="reimb-header-right">
          <div>
            <span>Status:</span>
            <div class="status ${status.toLowerCase()}">&#x25CF; ${status}</div>
          </div>
          ${
            userRoleId == 2 && status == "Pending"
              ? '<div class="manager-btns">' +
                `<button data-id=${reimb.reimbId} id="approve">Approve</button>` +
                `<button data-id=${reimb.reimbId} id="deny">Deny</button>` +
                "</div>"
              : ""
          }
        </div>
      </div>

      <div class="reimb-body">
        <div class="dates">
          <div class="submitted">
            <span>Submitted:</span>
            <p>${submitted}</p>
          </div>
          <div class="resolved">
            <span>Resolved:</span>
            <p>${resolved === "" ? "N/A" : resolved}</p>
          </div>
        </div>
        <div class="info">
          <div class="reimb-id">
            <span>Reimbursement ID:</span>
            <p>${reimb.reimbId}</p>
          </div>
          <div class="description">
            <span>${getReimbType(reimb.typeId)}</span>
            <p>${reimb.description}</p>
          </div>
        </div>
      </div>

      <div class="reimb-footer">
        <p>Reimbursement Amount</p>
        <h2>$${Intl.NumberFormat("en-US").format(reimb.amount)}</h2>
      </div>
    `;

    dashContainer.appendChild(reimbContainer);
  });
}

// Filter Dropdown Functionality
let filterElem = document.getElementById("filter");
let dropdown = document.getElementById("dropdown");
filterElem.addEventListener("click", () => {
  console.log("clicked");
  dropdown.classList.toggle("hidden");
});

let filterApproved = document.getElementById("filter-approved");
filterApproved.onclick = function (e) {
  e.preventDefault();

  let result = reimbursements.filter((reimb) => reimb.statusId === 1);
  getDataForUser(result);

  dropdown.classList.toggle("hidden");
};

let filterDenied = document.getElementById("filter-denied");
filterDenied.onclick = function (e) {
  e.preventDefault();

  let result = reimbursements.filter((reimb) => reimb.statusId === 2);
  getDataForUser(result);

  dropdown.classList.toggle("hidden");
};

let filterPending = document.getElementById("filter-pending");
filterPending.onclick = function (e) {
  e.preventDefault();

  let result = reimbursements.filter((reimb) => reimb.statusId === 3);
  getDataForUser(result);

  dropdown.classList.toggle("hidden");
};

let filterAll = document.getElementById("filter-all");
filterAll.onclick = function (e) {
  e.preventDefault();

  getDataForUser(reimbursements);

  dropdown.classList.toggle("hidden");
};

// logout button functionality
let logoutBtn = document.getElementById("logout");

logoutBtn.onclick = async function () {
  let logoutRes = await fetch(`${domain}/api/logout`);
  let logoutData = await logoutRes.json();

  if (logoutData.success) window.location = `${domain}`;
};

// click listener for approve and deny buttons
let reimbContainer = document.getElementById("dash-container");
reimbContainer.onclick = function (e) {
  if (e.target.id === "approve") {
    approve(e);
  } else if (e.target.id === "deny") {
    deny(e);
  }
};

// approve listener
async function approve(e) {
  e.preventDefault();
  console.log("approve clicked");
  let approveRes = await fetch(`${domain}/api/reimbursements`, {
    method: "PATCH",
    body: JSON.stringify({
      reimbId: e.target.dataset.id,
      statusId: 1,
    }),
  });

  let approveResData = await approveRes.json();

  if (approveResData.success) {
    reimbursements = await getReimbursements(userId);
    getDataForUser(reimbursements);
  }
}

// deny listener
async function deny(e) {
  e.preventDefault();
  console.log("deny clicked");
  let denyRes = await fetch(`${domain}/api/reimbursements`, {
    method: "PATCH",
    body: JSON.stringify({
      reimbId: e.target.dataset.id,
      statusId: 2,
    }),
  });

  let denyResData = await denyRes.json();

  if (denyResData.success) {
    reimbursements = await getReimbursements(userId);
    getDataForUser(reimbursements);
  }
}
