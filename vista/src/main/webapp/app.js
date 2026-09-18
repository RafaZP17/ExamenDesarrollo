document.addEventListener("DOMContentLoaded", () => {
    const navItems = document.querySelectorAll(".nav-item");
    const panels = document.querySelectorAll(".view-panel");

    navItems.forEach((item) => {
        item.addEventListener("click", (e) => {
            e.preventDefault();

            const target = item.getAttribute("data-target");

            navItems.forEach((nav) => nav.classList.remove("nav-item--active"));
            item.classList.add("nav-item--active");

            panels.forEach((panel) => {
                if (panel.id === `view-${target}`) {
                    panel.classList.add("active");
                } else {
                    panel.classList.remove("active");
                }
            });
        });
    });

    const setupModal = (btnId, modalId) => {
        const btn = document.getElementById(btnId);
        const modal = document.getElementById(modalId);
        if (!btn || !modal) return;

        const form = modal.querySelector("form");

        const closeModalAndReset = () => {
            modal.classList.remove("active");
            if (form) {
                form.reset();
            }
        };

        btn.addEventListener("click", () => {
            modal.classList.add("active");
        });

        const closeBtn = modal.querySelector(".modal-close");
        if (closeBtn) {
            closeBtn.addEventListener("click", closeModalAndReset);
        }

        modal.addEventListener("click", (e) => {
            if (e.target === modal) {
                closeModalAndReset();
            }
        });

        const submitBtn = modal.querySelector(".modal-submit-btn");
        if (submitBtn) {
            submitBtn.addEventListener("click", closeModalAndReset);
        }
    };

    setupModal("btn-open-profesor", "modal-profesor");
    setupModal("btn-open-asignatura", "modal-asignatura");
    setupModal("btn-open-asignacion", "modal-asignacion");
});