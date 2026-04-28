import { useState } from 'react';

function JourneySection() {
    const [activeTab, setActiveTab] = useState('guest');

    const guestSteps = [
        { title: "Download app/ Visit Website", image: "/guest_1.png" },
        { title: "Search for desired Car and book", image: "/guest_2.png" },
        { title: "Verify your profile", image: "/guest_3.png" },
        { title: "Get ready for your trip", image: "/guest_4.png" }
    ];

    const hostSteps = [
        { title: "Download App", image: "/host_1.png" },
        { title: "Register and Create Account", image: "/host_2.png" },
        { title: "Upload Car details and Images", image: "/host_3.png" },
        { title: "Start getting Bookings", image: "/host_4.png" }
    ];

    const currentSteps = activeTab === 'guest' ? guestSteps : hostSteps;

    return (
        <div className="journey-section">
            <div className="journey-toggle-container">
                <button
                    className={`journey-toggle-btn ${activeTab === 'guest' ? 'active' : ''}`}
                    onClick={() => setActiveTab('guest')}
                >
                    GUEST
                </button>
                <button
                    className={`journey-toggle-btn ${activeTab === 'host' ? 'active' : ''}`}
                    onClick={() => setActiveTab('host')}
                >
                    HOST
                </button>
            </div>

            <div className="journey-header">
                <h2 className="journey-title">
                    <span className="title-decorator"></span>
                    How to {activeTab === 'guest' ? 'book' : 'host'} a car on RoadReady
                    <span className="title-decorator"></span>
                </h2>
            </div>

            <div className="journey-grid">
                {currentSteps.map((step, index) => (
                    <div className="journey-card" key={index}>
                        <div className="journey-image-wrapper">
                            <img src={step.image} alt={step.title} />
                        </div>
                        <h4 className="journey-card-title">{step.title}</h4>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default JourneySection;
