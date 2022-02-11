package model;

// All possible object classifications an SCP/Entity can have
public enum Classification {
    UNCLASSIFIED, // For default Entity objects which represent empty database entries.
    SAFE,         // Entities that are easily and safely contained.
    EUCLID,       // Entities which require more resources to contain/containment may not be reliable.
    KETER,        // Entities which are extremely difficult to reliably contain with current technology.
    THAUMIEL,     // Entities the Foundation uses to contain other Entities.
    NEUTRALIZED,  // Entities which are no longer anomalous, having been destroyed or otherwise disabled.
    APOLLYON,     // Entities that cannot be contained/may escape imminently. Often associated with apocalypse.
    ARCHON        // Entities that could be contained, but that the Foundation decides are best left uncontained.
}
