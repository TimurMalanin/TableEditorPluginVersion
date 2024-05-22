package com.example.tableeditorpluginversion.graph

class DependencyGraph(private val dependencies: Map<Node, List<Node>>) {
    private val visited = mutableMapOf<Node, Boolean>()
    private val recursionStack = mutableMapOf<Node, Boolean>()
    private val topologicalOrder = mutableListOf<Node>()
    private val cycleNodes = mutableSetOf<Node>()

    fun buildAndSort(): Pair<List<Node>, Set<Node>> {
        visited.clear()
        recursionStack.clear()
        topologicalOrder.clear()
        cycleNodes.clear()

        dependencies.keys.forEach { node ->
            visited[node] = false
            recursionStack[node] = false
        }

        dependencies.keys.forEach { node ->
            if (visited[node] == false && !dfs(node)) {
                collectCycleNodes(node)
            }
        }

        return topologicalOrder.reversed() to cycleNodes
    }

    private fun dfs(node: Node): Boolean {
        if (recursionStack[node] == true) {
            cycleNodes.add(node)
            return false
        }
        if (visited[node] == true) return true

        visited[node] = true
        recursionStack[node] = true

        dependencies[node]?.forEach { neighbor ->
            if (!dfs(neighbor)) {
                cycleNodes.add(neighbor)
                return false
            }
        }

        recursionStack[node] = false
        topologicalOrder.add(node)
        return true
    }

    private fun collectCycleNodes(startNode: Node) {
        val stack = mutableListOf(startNode)
        val seen = mutableSetOf<Node>()

        while (stack.isNotEmpty()) {
            val node = stack.removeAt(stack.lastIndex)
            if (node !in seen) {
                seen.add(node)
                cycleNodes.add(node)
                stack.addAll(dependencies[node] ?: emptyList())
            }
        }
    }
}
