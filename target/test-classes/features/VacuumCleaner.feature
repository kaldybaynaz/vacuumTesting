Feature: Robotic Hoover Navigation
  As a user
  I want the hoover to navigate a room, follow instructions, and clean dirt patches
  So that I can verify its behavior and ensure it meets requirements

  Scenario Outline: Hoover navigates through the room and cleans patches
    Given the room size is <roomX> by <roomY>
    And the hoover starts at (<startX>, <startY>)
    And dirt patches are at <dirtPatches>
    When I send instructions "<instructions>"
    Then it ends at (<finalX>, <finalY>)
    And it should have cleaned <cleanedPatches> patches

    Examples:
      | roomX | roomY | startX | startY | dirtPatches          | instructions | finalX | finalY | cleanedPatches |
      | 5     | 5     | 1      | 2      | [(1,0), (2,2), (2,3)]| NNESEESWNWW  | 1      | 3      | 1              | # Happy Path
      | 3     | 3     | 0      | 0      | [(0,0), (1,1)]       | SSW          | 0      | 0      | 1              | # Encounters wall
      | 5     | 5     | 2      | 2      | [(1,0)]              | NNEAX        | 2      | 2      | 0              | # Invalid instruction
      | 4     | 4     | 2      | 2      | [(2,2), (3,3)]       | N            | 2      | 3      | 1              | # Starts on dirt
      | 5     | 5     | 2      | 2      | [(2,2), (3,3)]       |              | 2      | 2      | 1              | # No movement
      | 5     | 5     | 1      | 1      | []                   | NESW         | 1      | 1      | 0              | # No dirt patches
