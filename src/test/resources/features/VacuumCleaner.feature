Feature: Robotic Vacuum Navigation
  As a user
  I want to navigate a vacuum in a room and clean dirt patches
  So that I can verify its correct functioning and alignment with the specifications
  Scenario Outline: Vacuum navigates and cleans dirt patches
    Given a room of dimensions <roomX> by <roomY>
    And the vacuum starts at position <startX>, <startY>
    And dirt patches are at "<dirtLocations>"
    When I send the directions "<directions>"
    Then the vacuum should end at <endX>, <endY>
    And it should clean <spotsCleaned> dirt patches

    Examples:
      | roomX | roomY | startX | startY | dirtLocations         | directions   | endX | endY | spotsCleaned |
      | 5     | 5     | 1      | 2      | [(1,0), (2,2), (2,3)] | NNESEESWNWW  | 1    | 3    | 1            |
      | 3     | 3     | 0      | 0      | [(0,0), (1,1)]        | SSW          | 0    | 0    | 1            |
      | 5     | 5     | 2      | 2      | [(1,0)]               | NNEAX        | 2    | 2    | 0            |
      | 4     | 4     | 2      | 2      | [(2,2), (3,3)]        | N            | 2    | 3    | 1            |
      | 5     | 5     | 2      | 2      | [(2,2), (3,3)]        |              | 2    | 2    | 1            |
      | 5     | 5     | 1      | 1      | []                    | NESW         | 1    | 1    | 0            |
