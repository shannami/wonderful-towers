package domain.card

enum class CardEffect {
    MOVE_UP_2,
    MOVE_DOWN_2,
    SWAP_NEIGHBORS,
    SWAP_WITH_GAP,
    DESTROY_TOP,
    DESTROY_MIDDLE,
    DESTROY_BOTTOM
}